package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Complaint;
import com.model.Resident;
import com.repository.ComplaintRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class ComplaintController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/resident/complaint/new")
    public String showComplaintForm(Model model, HttpSession session,
                                    @RequestParam(value = "message", required = false) String message) {
        Object obj = session.getAttribute("resident");
        if (obj == null) {
            return "redirect:/resident?message=please_login";
        }

        Resident resident = (Resident) obj;
        model.addAttribute("residentUsername", resident.getUsername());
        model.addAttribute("message", message);

       
        java.util.List<Complaint> complaints = complaintRepository.findByResidentUsername(resident.getUsername());
        model.addAttribute("complaintCount", complaints == null ? 0 : complaints.size());
        return "resident/addComplaint";
    }

    @PostMapping("/resident/complaint")
    public String submitComplaint(@RequestParam String category,
                                  @RequestParam String description,
                                  HttpSession session) {

        Object obj = session.getAttribute("resident");
        if (obj == null) {
            return "redirect:/resident?message=please_login";
        }

        Resident resident = (Resident) obj;

        Complaint c = new Complaint();
        c.setCategory(category);
        c.setDescription(description);
        c.setStatus("Open");
        c.setResident(resident);

        complaintRepository.save(c);

       
        return "redirect:/resident/complaint/list?message=complaint_submitted";
    }

    @GetMapping("/resident/complaint/list")
    public String showComplaintList(Model model, HttpSession session,
                                    @RequestParam(value = "message", required = false) String message) {
        Object obj = session.getAttribute("resident");
        if (obj == null) {
            return "redirect:/resident?message=please_login";
        }

        Resident resident = (Resident) obj;
        java.util.List<Complaint> complaints = complaintRepository.findByResidentUsername(resident.getUsername());
        model.addAttribute("complaints", complaints);
        model.addAttribute("residentUsername", resident.getUsername());
        model.addAttribute("message", message);
        return "resident/complaintList";
    }

}
