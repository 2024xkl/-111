package com.loan.system_loan.controller;
import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.ResponseMessage;
import com.loan.system_loan.pojo.RoleType;
import com.loan.system_loan.pojo.User;
import com.loan.system_loan.pojo.dto.UserDto;
import com.loan.system_loan.pojo.dto.UserFilterDto;
import com.loan.system_loan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController//接口方法返回对象 转换成json文本
@RequestMapping("/user") //localhost:8080/user
public class UserController {

    @Autowired
    UserService userService;
    //REST
    //增加
//    @PostMapping ("/add")//前端请求地址：localhost:8080/user
//    public ResponseMessage<User> add(@Validated @RequestBody UserDto user){
//        User userNew =  userService.add(user);
//        return ResponseMessage.success(userNew);
//    }


//    @RequestMapping("/login")
//    public String login(@RequestBody UserDto userDto, HttpServletRequest request) {
//        // 获取前端传来的 cardId 和 password
//        String cardId = userDto.getCardId();
//        String password = userDto.getPassword();
//
//        // 假设从数据库获取用户信息，根据身份证号查找用户
//        User user = userService.getUser3(cardId);
//
//        if (user != null && user.getPassword().equals(password)) {
//            // 登录成功，设置角色
//            HttpSession session = request.getSession();
//            session.setAttribute("role", user.getRole());// 存储用户角色
//            session.setAttribute("userId", user.getUserId());  // 存储用户ID
//            return "登录成功";
//        } else {
//            return "身份证号或密码错误";
//        }
//    }
     @RequestMapping("/login")
     public ResponseEntity<?> login(@RequestBody UserDto userDto, HttpServletRequest request) {
         String cardId = userDto.getCardId();
         String password = userDto.getPassword();

         User user = userService.getUser3(cardId);

         if (user != null && user.getPassword().equals(password)) {
             HttpSession session = request.getSession();
             session.setAttribute("role", user.getRole());
             session.setAttribute("userId", user.getUserId());

             // 返回完整用户信息
             return ResponseEntity.ok(user);
         } else {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("身份证号或密码错误");
         }
     }



    //这下面的逻辑是没有问题的但是没有放回 用户的具体信息，所以改掉
    //    public String login(@RequestParam Integer userId, @RequestParam String password, HttpServletRequest request) {
//        // 假设从数据库获取用户信息
//        User user = userService.getUser1(userId);
//
//        if (user != null && user.getPassword().equals(password)) {
//            // 登录成功，设置角色
//            HttpSession session = request.getSession();  // 获取会话对象
//            session.setAttribute("role", user.getRole());
//            return "登录成功";
//        } else {
//            return "用户名或密码错误";
//        }
//    }
    //增加
    @PostMapping("/add") //前端请求地址：localhost:8080/user/add
    public ResponseMessage<User> add(@Validated @RequestBody UserDto user, BindingResult result) {
        // 如果校验失败，返回错误信息
        if (result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("; "));
            return ResponseMessage.error1("参数错误: " + sb.toString());
        }

        User userNew = userService.add(user, result);
        return ResponseMessage.success(userNew);
    }

    //新增查询端口
    @PostMapping("/getUserInfo")
    public ResponseMessage<User> getUserInfo(@RequestBody UserDto userDto) {
        // 校验是否传递了必要的数据
        if (userDto.getCardId() == null) {
            return ResponseMessage.error1("参数错误: cardId不能为空");
        }

        // 根据 DTO 中的 cardId 查询用户信息
        User user = userService.getUser3(userDto.getCardId());
        if (user == null) {
            return ResponseMessage.error1("用户不存在");
        }

        return ResponseMessage.success(user);
    }


    //查询
    @GetMapping("/findbyuserid/{userId}")
    public ResponseMessage<User> getById(@PathVariable Integer userId){
        User userNew =  userService.getUser1(userId);
        return ResponseMessage.success(userNew);
    }
    // 查询用户通过用户名
    @GetMapping("/findbyusername/{userName}")
    @RoleRequired(RoleType.CLERK)
    public ResponseMessage<List<User>> getByUsername(@PathVariable String userName) {
        List<User> userNew = userService.getUser2(userName);
        return ResponseMessage.success(userNew);
    }

    @GetMapping("/findbycardid/{cardId}")
    @RoleRequired(RoleType.CLERK)
    public ResponseMessage<User> getByCardId(@PathVariable String cardId) {
        User userNew = userService.getUser3(cardId);
        return ResponseMessage.success(userNew);
    }

    @PostMapping("/filter")
    @RoleRequired(RoleType.CLERK)
    public List<User> filterUsers(@RequestBody UserFilterDto filterDto) {
        return userService.filterUsers(filterDto);
    }


    //修改
    // 修改用户信息
    @RoleRequired(RoleType.USER)
    @PutMapping("/edit")
    public ResponseMessage<User> edit(@RequestBody @Valid UserDto user) {
        try {
            // 从请求头中获取当前用户 ID
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userIdHeader = request.getHeader("session");
            // 检查 session 是否为 null 或者为空字符串
            if (userIdHeader == null || userIdHeader.trim().isEmpty() || userIdHeader.equalsIgnoreCase("null")) {
                return ResponseMessage.error1("用户未登录，无法更新信息");
            }

            Integer currentUserId = Integer.parseInt(userIdHeader);


            System.out.println("当前用户ID (从 session 获取): " + currentUserId);
            System.out.println("请求的用户ID (从 body 获取): " + user.getUserId());
            // 验证用户是否只能修改自己的信息
            if (!currentUserId.equals(user.getUserId())) {
                return ResponseMessage.error1("用户只能修改自己的信息");
            }

            // 调用服务层的 edit 方法进行用户信息更新
            User updatedUser = userService.edit(user);
            return ResponseMessage.success(updatedUser);  // 返回修改后的用户信息
        } catch (Exception e) {
            return ResponseMessage.error1("更新失败: " + e.getMessage());  // 错误处理
        }
    }



    //删除
    @DeleteMapping("/delete/{userId}")
    public ResponseMessage<User> delete(@PathVariable Integer userId){
        userService.delete(userId);
        return ResponseMessage.success();
    }

    //查询所有的用户信息
    @GetMapping("/findall")
    @RoleRequired(RoleType.CLERK)
    public ResponseMessage<List<User>> getAllUser() {
        List<User> userNew = userService.getalluser();
        return ResponseMessage.success(userNew);
    }
}
