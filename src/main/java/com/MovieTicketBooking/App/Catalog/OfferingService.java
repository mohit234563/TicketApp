package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Common.CurrentTenant;
import com.MovieTicketBooking.App.Identity.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static com.MovieTicketBooking.App.Catalog.OfferingDtos.*;
import java.util.*;
@Service
public class OfferingService {
    private final OfferingRepository repo;
    private final OfferingStopRepository stopRepo;
    private final CurrentTenant currentTenant;
    private final TenantRepository tenantRepo;
    private final ServiceCategoryRepository serviceCatRepo;
    public OfferingService(OfferingRepository repo,OfferingStopRepository str,CurrentTenant ct,
                           TenantRepository tr,ServiceCategoryRepository scr){
        this.repo=repo;
        stopRepo=str;
        currentTenant=ct;
        tenantRepo=tr;
        serviceCatRepo=scr;

    }
    @Transactional(readOnly = true)
    public List<OfferingResponse>list(){
        return repo.findByTenantId(currentTenant.id()).stream().map(this::toResponse).toList();
    }
    @Transactional(readOnly = true)
    public OfferingResponse get(UUID id){
        return toResponse(find(id));
    }
    @Transactional
    public OfferingResponse create(OfferingRequest req){
        ServiceCategory cat=category(req.categoryCode());
        List<StopInput>stops=req.stops()==null?List.of():req.stops();
        validateStops(cat,stops);
        Offerings o=new Offerings();
        o.setTenant(tenantRepo.getReferenceById(currentTenant.id()));
        o.setActive(true);
        apply(o,cat,req);
        repo.save(o);
        saveStops(o.getId(),stops);
        return toResponse(o);

    }
    @Transactional
    public OfferingResponse update(UUID id,OfferingRequest req){
        Offerings o=find(id);
        ServiceCategory cat=category(req.categoryCode());
        List<StopInput>stops=req.stops()==null?List.of():req.stops();
        validateStops(cat,stops);
        apply(o,cat,req);
        stopRepo.deleteByOfferingId(id);
        saveStops(id,stops);
        return toResponse(o);
    }
    @Transactional
    public OfferingResponse setActive(UUID id,boolean active){
        Offerings o=find(id);
        o.setActive(active);
        return toResponse(o);
    }
    private void apply(Offerings o,ServiceCategory cat,OfferingRequest req){
        o.setCategory(cat);
        o.setTitle(req.title().trim());
        o.setDescription(req.description());
        o.setPosterUrl(req.posterUrl());
        o.setAttributes(req.attributes()==null?new HashMap<>():req.attributes());
    }
    private void saveStops(UUID id,List<StopInput>inputs){
        stopRepo.saveAll(inputs.stream().map(i->{
            OfferingStop s=new OfferingStop();
            s.setOfferingId(id);
            s.setCity(i.city().trim());
            s.setStopName(i.stopName().trim());
            s.setSeq(i.seq());
            s.setMinutesFromStart(i.minutesFromStart());
            s.setBoarding(i.boarding()==null||i.boarding());
            s.setDropping(i.dropping()==null ||i.dropping());
            return s;
        }).toList());
    }
    private void validateStops(ServiceCategory cat,List<StopInput>stops){
        if("bus".equals(cat.getCode()) && stops.size()<2){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"A bus route needs at least 2 stops.");
        }
        HashSet<Integer>seen=new HashSet<>();
        for(StopInput s:stops){
            if(!seen.add(s.seq())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Duplicate stop order number: " + s.seq());
            }
        }

    }
    private ServiceCategory category(String CategoryCode){
        return serviceCatRepo.findByCode(CategoryCode)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Unknown Category:"+CategoryCode));
    }
    private Offerings find(UUID id){
        return repo.findByIdAndTenantId(id,currentTenant.id())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Offering Not Found"));
    }
    private OfferingResponse toResponse(Offerings o){
        List<StopView>stops=stopRepo.findByOfferingIdOrderBySeq(o.getId()).stream().map(
                StopView::from).toList();
        return new OfferingResponse(o.getId(),o.getCategory().getCode(),o.getTitle(),o.getDescription(),
                o.getPosterUrl(),o.getAttributes(),o.isActive(),stops);
    }
}
