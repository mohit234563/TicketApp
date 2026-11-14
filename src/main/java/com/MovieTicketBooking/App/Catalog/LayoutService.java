package com.MovieTicketBooking.App.Catalog;

import com.MovieTicketBooking.App.Common.CurrentTenant;
import com.MovieTicketBooking.App.Identity.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static com.MovieTicketBooking.App.Catalog.LayoutDtos.*;

import java.util.*;

@Service
public class LayoutService {
    private final LayoutRepository layoutRepo;
    private final TenantRepository tenantRepo;
    private final CurrentTenant currentTenant;
    private final SessionRepository sessionRepo;
    private final SeatRepository seatRepo;
    public LayoutService(LayoutRepository layoutRepo, TenantRepository tenantRepo, CurrentTenant currentTenant,SessionRepository sessionRepo,SeatRepository seatRepo){
        this.layoutRepo=layoutRepo;
        this.tenantRepo=tenantRepo;
        this.sessionRepo=sessionRepo;
        this.currentTenant=currentTenant;
        this.seatRepo=seatRepo;
    }
    @Transactional(readOnly = true)
    public List<layoutSummary> list(){
        return layoutRepo.findByTenantId(currentTenant.id()).stream().map(
                l->new layoutSummary(l.getId(),l.getName(),seatRepo.countByLayoutId(l.getId()))
        ).toList();
    }
    @Transactional(readOnly = true)
    public LayoutResponse get(UUID id){
        Layouts layout=find(id);
        return  LayoutResponse.from(layout,seatRepo.findByLayoutId(id));
    }
    @Transactional
    public LayoutResponse create(LayoutRequest req){
        requireUniqueLabels(req.seats());
        Layouts l=new Layouts();
        l.setName(req.name());
        l.setLayoutJson(req.layoutJson());
        l.setTenant(tenantRepo.getReferenceById(currentTenant.id()));
        l=layoutRepo.save(l);
        List<Seats>saved=seatRepo.saveAll(toSeats(l,req.seats()));
        return LayoutResponse.from(l,saved);
    }
    @Transactional
    public LayoutResponse update(UUID id,LayoutRequest req){
        Layouts l=find(id);
        if(sessionRepo.existsByResource_layout_id(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"This layout is used by scheduled trips or shows. Clone it and edit the copy instead.");
        }
        l.setName(req.name().trim());
        l.setLayoutJson(req.layoutJson());
        seatRepo.deleteByLayoutId(id);
        List<Seats>saved=seatRepo.saveAll(toSeats(l,req.seats()));
        return LayoutResponse.from(l,saved);

    }
    @Transactional
    public LayoutResponse clone(UUID id,String newName){
        Layouts l=find(id);
        List<SeatInput>seats=seatRepo.findByLayoutId(id).stream().map(s->new SeatInput(s.getLabel(),s.getSection(),s.getRowNo(),s.getColNo(),s.getAttributes())).toList();
        return create(new LayoutRequest(newName,l.getLayoutJson(),seats));
    }
    @Transactional
    public void delete(UUID id){
        layoutRepo.delete(find(id));
        layoutRepo.flush();
    }
    private Layouts find(UUID id){
        return layoutRepo.findByIdAndTenantId(id,currentTenant.id())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"layout not found"));
    }
    private void requireUniqueLabels(List<SeatInput>seats){
        Set<String>seen=new HashSet<>();
        for(SeatInput s:seats){
            if(!seen.add(s.label().trim())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Duplication labels are not allowed: "+s.label().trim());
            }
        }
    }
    private List<Seats>toSeats(Layouts layout,List<SeatInput>inputs){
        return inputs.stream().map(i->{
          Seats s=new Seats();
          s.setLayout(layout);
          s.setLabel(i.label().trim());
          s.setRowNo(i.rowNo());
          s.setColNo(i.colNo());
          s.setAttributes(i.attributes()==null?new HashMap<>():i.attributes());
          s.setSection(i.section());
          return s;
        }).toList();
    }
}
