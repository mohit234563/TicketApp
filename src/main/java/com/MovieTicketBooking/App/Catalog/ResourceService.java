package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Common.CurrentTenant;
import com.MovieTicketBooking.App.Identity.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import static com.MovieTicketBooking.App.Catalog.ResponseDtos.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
@Service
public class ResourceService {
    private final ResourceRepository repo;
    private final LayoutRepository layoutRepo;
    private final CurrentTenant currentTenant;
    private final SessionRepository sessionRepo;
    private final SeatRepository seatRepo;
    private final TenantRepository tenantRepo;
    private final VenueRepository venueRepo;
    public ResourceService(ResourceRepository rr,LayoutRepository lr,CurrentTenant ct,SessionRepository sr,
                           SeatRepository str,TenantRepository tr,VenueRepository vr){
        repo=rr;
        layoutRepo=lr;
        currentTenant=ct;
        sessionRepo=sr;
        seatRepo=str;
        tenantRepo=tr;
        venueRepo=vr;
    }
    @Transactional(readOnly = true)
    public List<ResourceResponse>list(){
        return repo.findByTenantId(currentTenant.id()).stream().map(ResourceResponse::from).toList();
    }
    @Transactional(readOnly = true)
    public ResourceResponse get(UUID id){
        return ResourceResponse.from(find(id));
    }
    @Transactional
    public ResourceResponse create(ResourceRequest req){
        Resources r=new Resources();
        r.setTenant(tenantRepo.getReferenceById(currentTenant.id()));
        apply(r,req);
        return ResourceResponse.from(repo.save(r));
    }
    @Transactional
    public ResourceResponse update(UUID id,ResourceRequest req){
        Resources r=find(id);
        UUID oldLayoutId=r.getLayout()==null?null:r.getLayout().getId();
        if(!Objects.equals(oldLayoutId,req.layoutId()) && sessionRepo.existsByResource_id(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
            "This bus/screen already has scheduled sessions, so its seat layout can't be changed.");
        }
        apply(r,req);
        return ResourceResponse.from(r);
    }
    @Transactional
    public void delete(UUID id){
        repo.delete(find(id));
        repo.flush();
    }

    private Resources find(UUID id){
        return repo.findByIdAndTenantId(id,currentTenant.id())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
    }
    private void apply(Resources r,ResourceRequest req){
        UUID tenantId=currentTenant.id();
        Layouts l=req.layoutId()==null?null
                :layoutRepo.findByIdAndTenantId(req.layoutId(),tenantId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Layout not found"));
        Venues v=req.venueId()==null?null
                :venueRepo.findByIdAndTenantId(req.venueId(),tenantId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Venue not found"));
        Integer capacity= req.capacity();
        if(capacity==null && l!=null){
            capacity=(int)seatRepo.countByLayoutId(l.getId());
        }
        r.setName(req.name().trim());
        r.setVenue(v);
        r.setLayout(l);
        r.setCapacity(capacity);
        r.setAttributes(req.attributes()==null?new HashMap<>():req.attributes());
    }
}
