package com.cheng.biProject.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * 通过AI生成图表请求
 *

 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}