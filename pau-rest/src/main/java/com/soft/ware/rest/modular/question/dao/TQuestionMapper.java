package com.soft.ware.rest.modular.question.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.question.model.TQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题反馈 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface TQuestionMapper extends BaseMapper<TQuestion> {

    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> map);

}
