package com.project.demo.rest.publication;

import com.project.demo.logic.entity.http.Meta;
import com.project.demo.logic.entity.publication.TblPublicationRepository;
import com.project.demo.logic.entity.user.TblUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/puiblications")
public class PublicationRestController {

    @Autowired
    private TblPublicationRepository tblPublicationRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    HttpServletRequest request){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<TblUser> ordersPage = tblPublicationRepository.findAll(pageable);
        Meta meta = new Meta(request.getMethod(), request.getRequestURL().toString());
        meta.setTotalPages(ordersPage.getTotalPages());
        meta.setTotalElements(ordersPage.getTotalElements());
        meta.setPageNumber(ordersPage.getNumber() + 1);
        meta.setPageSize(ordersPage.getSize());

    }
}
