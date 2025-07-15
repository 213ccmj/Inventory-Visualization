package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.User;
import com.wms.service.IGoodsService;
import com.wms.service.IGoodstypeService;
import com.wms.service.IStorageService;
import com.wms.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2025-04-22
 */
@RestController
@RequestMapping("/home")
public class HomeController {
    @Resource
    IUserService iUserService;

    @Resource
    IGoodsService  iGoodsService;

    @Resource
    IGoodstypeService iGoodstypeService;

    @Resource
    IStorageService iStorageService;

    //    首页统计数据
    @GetMapping("/statistics")
    Result getHomeStatistics(){
        HashMap<String, Long> m = new HashMap<>();
        m.put("goodsCount",iGoodsService.count());
        m.put("goodsTypeCount", iGoodstypeService.count());
        m.put("storageCount", iStorageService.count());
        m.put("userCount", iUserService.count());
        return Result.success(m);
    }
}
