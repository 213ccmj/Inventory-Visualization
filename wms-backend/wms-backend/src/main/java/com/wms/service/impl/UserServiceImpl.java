package com.wms.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wms.dto.UserDto;
import com.wms.common.Result;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.mapper.UserMapper;
import com.wms.service.IMenuService;
import com.wms.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.service.uitls.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2025-05-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapperl;

    @Autowired
    IMenuService iMenuService;


    @Override
    public boolean save(User user) {
        return userMapperl.insert(user)==1;
    }

    @Override
    public boolean del(Integer id) {
        return userMapperl.deleteById(id)==1;
    }

    @Override
    public boolean mod(User user) {
        return userMapperl.updateById(user)==1;
    }

    @Override
    public List<User> get(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,user.getName());
        return userMapperl.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<User> getAll() {
        return userMapperl.selectAll();
    }

    @Override
    public Result login(String num, String password) {
        User user1 =getOne(new QueryWrapper<User>().eq("num",num));
        if(user1 !=null){
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getNum,num);

            lambdaQueryWrapper.eq(User::getPassword, password);
            User user = userMapperl.selectOne(lambdaQueryWrapper);
            System.out.println("User==============================="+user);
            if(user!=null){
                //动态路由
                List<Menu> list = iMenuService.lambdaQuery().like(Menu::getMenuRight,user.getGrade()).list();
                HashMap res = new HashMap();
                res.put("user",user);
                res.put("menu",list);

                UserDto userDto = new UserDto();
                BeanUtils.copyProperties(user, userDto);
                //设置token
                String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPassword());
                userDto.setToken(token);

                return Result.success(res);
            }else{
                return Result.fail();
            }
        }else{
            return Result.fail();
        }

    }


}
