/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lms.letter_management_system.service;

import com.lms.letter_management_system.datatable.DataTableRepo;
import com.lms.letter_management_system.datatable.DataTableRequest;
import com.lms.letter_management_system.datatable.DataTablesResponse;
import com.lms.letter_management_system.dto.GetPagesDTO;
import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.dto.UserDataTable;
import com.lms.letter_management_system.dto.UserTypeDataTable;
import com.lms.letter_management_system.model.UserType;
import com.lms.letter_management_system.model.User;
import com.lms.letter_management_system.repository.PageRepo;
import com.lms.letter_management_system.repository.UserRepo;
import com.lms.letter_management_system.repository.UserTypeRepo;
import jakarta.servlet.http.HttpSession;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rusir
 */
@Service
public class AdminService {

    @Autowired
    PageRepo pageRepo;

    @Autowired
    UserTypeRepo userTypeRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private DataTableRepo<UserDataTable> userDt;

//     @Autowired
//     SalaryRepo SalaryRepo;

//     @Autowired
//     LeaveRepo LeaveRepo;

//     @Autowired
//     UserDesignationRepo userDesignationRepo;

    @Autowired
    private DataTableRepo<UserTypeDataTable> userTypeDt;

//     public DataTablesResponse<UserDataTable> getUsers(DataTableRequest param) throws Exception {
//         return userDt.getData(UserDataTable.class, param, "select `id`,`username`,`name`,(select `name` from `user_type` where `id`=u.`user_type`) as `userType`,(select `username` from `user` where `id`=u.`mod_by`) as `ModBy`,`mod_on`,`status` FROM `user` u WHERE true ");
//     }

    public DataTablesResponse<UserDataTable> getUsers(DataTableRequest param) throws Exception {
        return userDt.getData(UserDataTable.class, param, "SELECT `id`,`username`,`full_name`,(select `name` from `user_type` where `id`=u.`user_type`) as `userType`,`status` FROM `users` u WHERE true ");
    }

    public User getUser(Integer id) throws Exception {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        if (user.getUserType() != null && user.getUserType().getId() != null) {
            UserType utype = userTypeRepo.findById(user.getUserType().getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "User type not found for user id: " + id));
            user.setUserTypeName(utype.getName());
        }

        return user;
    }

    public Iterable<SlimSelectDTO> getUserTypeIdAndName(String search) {
        String value = search == null ? "%" : "%" + search.trim() + "%";
        return userTypeRepo.getIdAndName(value);
    }

    public Iterable<SlimSelectDTO> getUserDesignationIdAndName(String search) {
        return java.util.Collections.emptyList();
    }

//     public Salary getSalary(Integer id) throws Exception {
//         Salary s1 = SalaryRepo.findById(id).get();
// //        User  u1 = userRepo.findById(s1.getEmpId().get()).get();
// //        s1.setEmpName(u1.getName());
//         return s1;
//     }

    public DataTablesResponse<UserTypeDataTable> getUserType(DataTableRequest param) throws Exception {
        return userTypeDt.getData(UserTypeDataTable.class, param, "SELECT `id`,`name`,(SELECT `username` FROM `users` WHERE `id`=u.`mod_by`) AS `mod_by`,`mod_on`,`status` FROM `user_type` u WHERE TRUE ");
    }

    public GetPagesDTO getPage() {
//    public Iterable<Page> getPage() {
        System.out.println(pageRepo.getPage());
        return pageRepo.getPage();
    }

    public GetPagesDTO getSelectedPage(Integer id) {
//    public Iterable<Page> getPage() {
        return pageRepo.getSelectedPage(id);
    }

//     public UserType saveUserType(String name, String dashboard, String pages) throws Exception {

//         List<Integer> pageNumbers = Arrays.asList(new ObjectMapper().readValue(pages, Integer[].class));

//         String pagesJson = new ObjectMapper().writeValueAsString(pageNumbers);

//         UserType page = new UserType();
//         page.setDashboard(dashboard);
//         page.setName(name);
//         page.setPages(pagesJson);
//         page.setStatus("active");

//         page = userTypeRepo.save(page);

//         return page;

//     }

//     public UserType updateUserType(Integer id, String name, String dashboard, String pages) throws Exception {

//         List<Integer> pageNumbers = Arrays.asList(new ObjectMapper().readValue(pages, Integer[].class));

//         String pagesJson = new ObjectMapper().writeValueAsString(pageNumbers);

//         UserType page = userTypeRepo.findById(id).get();
//         page.setDashboard(dashboard);
//         page.setName(name);
//         page.setPages(pagesJson);
//         page.setStatus("active");

//         page = userTypeRepo.save(page);

//         return page;

//     }

//     public User saveUser(String name, String username, Integer userType, String address, Integer tpno, String email, String password, Integer empno, Integer designation) throws Exception {
//         User user = new User();
//         user.setUsername(username);
//         user.setName(name);
//         user.setUserType(AggregateReference.to(userType));
//         user.setAddress(address);
//         user.setMobile(tpno);
//         user.setEmail(email);
//         user.setPassword(password);
//         user.setEmpNo(empno);
//         user.setDesignation(AggregateReference.to(designation));
//         user.setStatus("active");
//         user = userRepo.save(user);
//         return user;
//     }

//     public Salary saveSalary(Integer basic, Integer allowance, Double total, Integer deduct, Double net, Integer emp) throws Exception {
//         Salary s1 = new Salary();
//         s1.setAllowances(allowance);
//         s1.setBasicSalary(basic);
//         s1.setDeduction(deduct);
//         s1.setEmpId(emp);
//         s1.setNetSalary(net);
//         s1.setTolSalary(total);
//         s1 = SalaryRepo.save(s1);
//         return s1;
//     }

//     public Leave saveLeave(HttpSession session, String leaveYear, String leaveType, String from, String to, String comment, Integer emp) throws Exception {
//         Integer uid = (Integer) session.getAttribute("uid");
        
//         Leave leave = new Leave();
//         leave.setLeaveType(leaveType);
//         leave.setLeaveYear(leaveYear);
//         leave.setFromDate(from);
//         leave.setToDate(to);
//         leave.setComments(comment);
//         leave.setEmpId(uid);
//         leave.setApprover(emp);

//         leave = LeaveRepo.save(leave);
//         return leave;
//     }

//     public Salary updateSalary(Integer basic, Integer allowance, Double total, Integer deduct, Double net, Integer emp) throws Exception {
//         Salary s1 = new Salary();
//         s1.setAllowances(allowance);
//         s1.setBasicSalary(basic);
//         s1.setDeduction(deduct);
//         s1.setEmpId(emp);
//         s1.setNetSalary(net);
//         s1.setTolSalary(total);
//         s1 = SalaryRepo.save(s1);
//         return s1;
//     }

//     public User updateUser(Integer id, String name, String username, Integer userType, String address, Integer tpno, String email, String password, Integer empno, Integer designation) throws Exception {

//         User user = userRepo.findById(id).get();
//         user.setUsername(username);
//         user.setName(name);
//         user.setUserType(AggregateReference.to(userType));
//         user.setAddress(address);
//         user.setMobile(tpno);
//         user.setEmail(email);
//         user.setPassword(password);
//         user.setEmpNo(empno);
//         user.setDesignation(AggregateReference.to(designation));
//         user = userRepo.save(user);
//         return user;
//     }

//     public UserType deactivateUserType(Integer id) throws Exception {
//         UserType utype = userTypeRepo.findById(id).get();
//         utype.setStatus("deactivated");
//         utype = userTypeRepo.save(utype);
//         return utype;
//     }

    public UserType reactivateUserType(Integer id) throws Exception {
        UserType utype = userTypeRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User type not found with id: " + id));
        utype.setStatus("active");
        utype = userTypeRepo.save(utype);
        return utype;
    }

    public User deactivateUser(Integer id) throws Exception {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setStatus("deactivated");
        user = userRepo.save(user);
        return user;
    }

    public User reactivateUser(Integer id) throws Exception {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        user.setStatus("active");
        user = userRepo.save(user);
        return user;
    }

//     public Iterable<SlimSelectDTO> getDashboard(String search) {
//         return userTypeRepo.getDashboard("%" + search.trim() + "%");
//     }

//     public Iterable<SlimSelectDTO> getUserTypeIdAndName(String search) {
//         return userTypeRepo.getIdAndName("%" + search.trim() + "%");
//     }

//     public Iterable<SlimSelectDTO> getEmpIdAndName(String search) {
//         return userRepo.getIdAndName("%" + search.trim() + "%");
//     }

//     public Iterable<SlimSelectDTO> getUserDesignationIdAndName(String search) {
//         return userDesignationRepo.getIdAndName("%" + search.trim() + "%");
//     }
}
