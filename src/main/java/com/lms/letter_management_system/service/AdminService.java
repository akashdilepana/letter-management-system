/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lms.letter_management_system.service;

import com.lms.letter_management_system.datatable.DataTableRepo;
import com.lms.letter_management_system.datatable.DataTableRequest;
import com.lms.letter_management_system.datatable.DataTablesResponse;
import com.lms.letter_management_system.dto.DesignationDataTable;
import com.lms.letter_management_system.dto.DivisionLvlDesDataTable;
import com.lms.letter_management_system.dto.GetPagesDTO;
import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.dto.UserDataTable;
import com.lms.letter_management_system.dto.UserTypeDataTable;
import com.lms.letter_management_system.model.UserType;
import com.lms.letter_management_system.model.DivisionLvlDes;
import com.lms.letter_management_system.model.TopLevelDes;
import com.lms.letter_management_system.model.User;
import com.lms.letter_management_system.repository.DivisionLvlDesRepo;
import com.lms.letter_management_system.repository.PageRepo;
import com.lms.letter_management_system.repository.TopLevelDesRepo;
import com.lms.letter_management_system.repository.UserRepo;
import com.lms.letter_management_system.repository.UserTypeRepo;
import jakarta.servlet.http.HttpSession;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.Data;
import tools.jackson.databind.ObjectMapper;

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
    TopLevelDesRepo TopLevelDesRepo;

    @Autowired
    DivisionLvlDesRepo DivisionLvlDesRepo;

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

    @Autowired
    private DataTableRepo<DesignationDataTable> TopLvlDesDt;

    @Autowired
    private DataTableRepo<DivisionLvlDesDataTable> DivisionLvlDesDt;

    public DataTablesResponse<DesignationDataTable> getDesignations(DataTableRequest param) throws Exception {
        return TopLvlDesDt.getData(DesignationDataTable.class, param, "select `id`,`name`,(select `username` from `users` where `id`=t.`mod_by`) as `ModBy`,`mod_on`,`status` FROM `top_level_designation` t WHERE true");
    }

    public DataTablesResponse<UserDataTable> getUsers(DataTableRequest param) throws Exception {
        return userDt.getData(UserDataTable.class, param, "SELECT `id`,`username`,`full_name`,(select `name` from `user_type` where `id`=u.`user_type`) as `userType`,`status` FROM `users` u WHERE true ");
    }

    public User getUser(Integer id) throws Exception {
        System.out.println("hiiiiii");
        User user = userRepo.findById(id).get();
        UserType utype = userTypeRepo.findById(user.getUserType().getId()).get();
        user.setUserTypeName(utype.getName());
        return user;
    }

    public TopLevelDes getTopLvlDes(Integer id) throws Exception {
        System.out.println("hiiiiii");
        TopLevelDes topLevelDes = TopLevelDesRepo.findById(id).get();
        return topLevelDes;
    }

    public TopLevelDes saveTopLvlDes(String name) throws Exception {
        TopLevelDes topLevelDes = new TopLevelDes();
        topLevelDes.setName(name);
        topLevelDes.setStatus("active");
        topLevelDes = TopLevelDesRepo.save(topLevelDes);
        return topLevelDes;
    }

    public TopLevelDes updateTopLvlDes(Integer id, String name) throws Exception {

        TopLevelDes topLevelDes = TopLevelDesRepo.findById(id).get();
        topLevelDes.setName(name);
        topLevelDes = TopLevelDesRepo.save(topLevelDes);
        return topLevelDes;
    }
   
    public TopLevelDes deactivateTopLvlDes(Integer id) throws Exception {
        TopLevelDes topLevelDes = TopLevelDesRepo.findById(id).get();
        topLevelDes.setStatus("deactive");
        topLevelDes = TopLevelDesRepo.save(topLevelDes);
        return topLevelDes;
    }

    public TopLevelDes reactivateTopLvlDes(Integer id) throws Exception {
        TopLevelDes topLevelDes = TopLevelDesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Designation not found with id: " + id));
        topLevelDes.setStatus("active");
        topLevelDes = TopLevelDesRepo.save(topLevelDes);
        return topLevelDes;
    }

    //division level designation

    public DataTablesResponse<DivisionLvlDesDataTable> getDivisionLvlDes(DataTableRequest param) throws Exception {
        return DivisionLvlDesDt.getData(DivisionLvlDesDataTable.class, param, "select `id`,`name`,(select `username` from `users` where `id`=t.`mod_by`) as `ModBy`,`mod_on`,`status` FROM `division_level_designation` t WHERE true");
    }

    public DivisionLvlDes getDivisionLvlDes(Integer id) throws Exception {
        System.out.println("hiiiiii");
        DivisionLvlDes divisionLvlDes = DivisionLvlDesRepo.findById(id).get();
        return divisionLvlDes;
    }

    public DivisionLvlDes saveDivisionLvlDes(String name) throws Exception {
        DivisionLvlDes divisionLvlDes = new DivisionLvlDes();
        divisionLvlDes.setName(name);
        divisionLvlDes.setStatus("active");
        divisionLvlDes = DivisionLvlDesRepo.save(divisionLvlDes);
        return divisionLvlDes;
    }

    public DivisionLvlDes updateDivisionLvlDes(Integer id, String name) throws Exception {

        DivisionLvlDes divisionLvlDes = DivisionLvlDesRepo.findById(id).get();
        divisionLvlDes.setName(name);
        divisionLvlDes = DivisionLvlDesRepo.save(divisionLvlDes);
        return divisionLvlDes;
    }
   
    public DivisionLvlDes deactivateDivisionLvlDes(Integer id) throws Exception {
        DivisionLvlDes divisionLvlDes = DivisionLvlDesRepo.findById(id).get();
        divisionLvlDes.setStatus("deactive");
        divisionLvlDes = DivisionLvlDesRepo.save(divisionLvlDes);
        return divisionLvlDes;
    }

    public DivisionLvlDes reactivateDivisionLvlDes(Integer id) throws Exception {
        DivisionLvlDes divisionLvlDes = DivisionLvlDesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Designation not found with id: " + id));
        divisionLvlDes.setStatus("active");
        divisionLvlDes = DivisionLvlDesRepo.save(divisionLvlDes);
        return divisionLvlDes;
    }  

    // end
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

    public DataTablesResponse<UserTypeDataTable> getLetters(DataTableRequest param) throws Exception {
        return userTypeDt.getData(UserTypeDataTable.class, param, "SELECT `id`,`name`,(SELECT `username` FROM `users` WHERE `id`=u.`mod_by`) AS `mod_by`,`mod_on`,`status` FROM `user_type` u WHERE TRUE ");
    }

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

    public UserType saveUserType(String name, String pages) throws Exception {

        List<Integer> pageNumbers = Arrays.asList(new ObjectMapper().readValue(pages, Integer[].class));

        String pagesJson = new ObjectMapper().writeValueAsString(pageNumbers);

        UserType page = new UserType();
        // page.setDashboard(dashboard);
        page.setName(name);
        page.setPages(pagesJson);
        page.setStatus("active");

        page = userTypeRepo.save(page);

        return page;

    }

    public UserType updateUserType(Integer id, String name, String pages) throws Exception {

        List<Integer> pageNumbers = Arrays.asList(new ObjectMapper().readValue(pages, Integer[].class));

        String pagesJson = new ObjectMapper().writeValueAsString(pageNumbers);

        UserType page = userTypeRepo.findById(id).get();
        // page.setDashboard(dashboard);
        page.setName(name);
        page.setPages(pagesJson);
        page.setStatus("active");

        page = userTypeRepo.save(page);

        return page;

    }

    public User saveUser(String name, String username, Integer userType, String email) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setFull_name(name);
        user.setUserType(AggregateReference.to(userType));
        user.setEmail(email);
        user.setStatus("active");
        user = userRepo.save(user);
        return user;
    }

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

    public User updateUser(Integer id, String name, String username, Integer userType, String email) throws Exception {

        User user = userRepo.findById(id).get();
        user.setUsername(username);
        user.setFull_name(name);
        user.setUserType(AggregateReference.to(userType));
        user.setEmail(email);
        user = userRepo.save(user);
        return user;
    }

    public UserType deactivateUserType(Integer id) throws Exception {
        UserType utype = userTypeRepo.findById(id).get();
        utype.setStatus("deactive");
        utype = userTypeRepo.save(utype);
        return utype;
    }

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
        user.setStatus("deactive");
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
