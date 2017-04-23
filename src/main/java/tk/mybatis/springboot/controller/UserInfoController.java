/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import tk.mybatis.springboot.modal.ResponseModal;
import tk.mybatis.springboot.model.UserInfo;
import tk.mybatis.springboot.service.RedisService;
import tk.mybatis.springboot.service.UserInfoService;

import java.util.List;

/**
 * @author liuzh
 * @since 2015-12-19 11:10
 */
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private RedisService redisService;

    @RequestMapping
    public PageInfo<UserInfo> getAll(UserInfo userInfo) {
        List<UserInfo> userInfoList = userInfoService.getAll(userInfo);
        return new PageInfo<UserInfo>(userInfoList);
    }

    @RequestMapping(value = "/add")
    public UserInfo add() {
        return new UserInfo();
    }

    @RequestMapping(value = "/view/{id}")
    public UserInfo view(@PathVariable Integer id) {
        ModelAndView result = new ModelAndView();
        UserInfo userInfo = userInfoService.getById(id);
        return userInfo;
    }

    @RequestMapping(value = "/delete/{id}")
    public ModelMap delete(@PathVariable Integer id) {
        ModelMap result = new ModelMap();
        userInfoService.deleteById(id);
        result.put("msg", "删除成功!");
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelMap save(UserInfo userInfo) {
        ModelMap result = new ModelMap();
        String msg = userInfo.getId() == null ? "新增成功!" : "更新成功!";
        userInfoService.save(userInfo);
        result.put("userInfo", userInfo);
        result.put("msg", msg);
        return result;
    }

    //从redis获取某个用户
    /*@RequestMapping(value = "/getUserFromRedis", method = RequestMethod.GET)
    public @ResponseBody UserInfo getRedis(@RequestParam String key) {
        return (UserInfo)redisService.get(key);
    }*/

    //获取所有用户
    /*@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public @ResponseBody
    Page<UserInfo> list(Model model, Pageable pageable){
        return userInfoService.findAll(pageable);
    }*/

    //添加用户
    @GetMapping(value="/addUser")
    public @ResponseBody String addUser(@RequestParam String dictum,
                                        @RequestParam String password, @RequestParam String username) {
        UserInfo user = new UserInfo();
        user.setPassword(password);
        user.setUsername(username);
        System.out.println(user);
        userInfoService.save(user);
        //redisService.set(user.getId()+"", user);
        return "Saved";
    }

    @RequestMapping("/redis/set")
    public ResponseModal redisSet(@RequestParam("value")String value){
        boolean isOk = redisService.set("name", value);
        return new ResponseModal(isOk ? 200 : 500, isOk, isOk ? "success" : "error" , null);
    }

    @RequestMapping("/redis/get")
    public ResponseModal redisGet(){
        Object name = redisService.query("name");
        return new ResponseModal(200, true,"success",name);
    }
}
