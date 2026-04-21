package com.year2.queryme.controller;

import com.year2.queryme.model.Admin;
import com.year2.queryme.repository.AdminRepository;
import com.year2.queryme.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import com.year2.queryme.model.RegistrationRequest;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public Admin register(@RequestBody Map<String, String> data) {
        return adminService.registerAdmin(
                data.get("email"),
                data.get("password"),
                data.get("fullName")
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Admin update(@PathVariable Long id, @RequestBody Map<String, String> data) {
        return adminService.updateProfile(id, data);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Admin> getAll(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @GetMapping("/registration-requests")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RegistrationRequest> getRegistrationRequests() {
        return adminService.getPendingRegistrationRequests();
    }

    @PostMapping("/registration-requests/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public void approveRequest(@PathVariable UUID id) {
        adminService.approveRegistrationRequest(id);
    }

    @PostMapping("/registration-requests/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public void rejectRequest(@PathVariable UUID id, @RequestBody Map<String, String> data) {
        adminService.rejectRegistrationRequest(id, data.get("reason"));
    }
}
