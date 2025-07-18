package com.wms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@TableName("menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单编码
     */
    @TableField("menuCode")
    private String menuCode;

    /**
     * 菜单名字
     */
    @TableField("menuName")
    private String menuName;

    /**
     * 菜单级别
     */
    @TableField("menuLevel")
    private String menuLevel;

    /**
     * 菜单的父code
     */
    @TableField("menuParentCode")
    private String menuParentCode;

    /**
     * 点击触发的函数
     */
    @TableField("menuClick")
    private String menuClick;

    /**
     * 权限 0超级管理员 1管理员 2普通用户 可以用逗号组合使用
     */
    @TableField("menuRight")
    private String menuRight;

    /**
     * 页面路径
     */
    @TableField("menuComponent")
    private String menuComponent;

    @TableField("menuIcon")
    private String menuIcon;


    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", menuCode=" + menuCode +
            ", menuName=" + menuName +
            ", menuLevel=" + menuLevel +
            ", menuParentCode=" + menuParentCode +
            ", menuClick=" + menuClick +
            ", menuRight=" + menuRight +
            ", menuComponent=" + menuComponent +
            ", menuIcon=" + menuIcon +
        "}";
    }
}
