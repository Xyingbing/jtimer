package com.itzhoujun.jtimer.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.itzhoujun.jtimer.entity.Setting;

public interface SettingMapper extends BaseMapper<Setting> {

    Integer updateValueById(Setting t);
}
