package com.xavier.trans.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xavier.trans.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "pt_petition_high_level_reduce", type = "pt_petition_high_level_reduce")
public class PetitionHighLevelReduce extends BaseEntity {

	@Id
	private String id;/* ES记录Id */

	@Field
	private String dataSource;/* 数据来源 sjly */

	@Field
	private String officeId;/* 归属机构 */

	@Field
	private String departmentId;/* 归属部门 */

	@Field
	private String queryCode;/* 查询码 */

	@Field
	private String petitionCaseNo;/* 信访件编号 */

	@Field
	private String repeatNo;/* 重复信访件编号 */

	@Field
	private String petitionTypeCode;/* 信访形式 */

	@Field(type = FieldType.Integer)
	private Integer petitionManAmount;/* 信访人数 */

	@Field(type = FieldType.Integer)
	private Integer involveManAmount;/* 涉及人数 */

	@Field
	private String registerOfficeCode;/* 登记机构代码 */

	@Field
	private String registerOfficeName;/* 登记机构名称 */

	@Field
	private String registerOfficeCategory;/* 登记机构类别 */

	@Field
	private String registerDepartmentId;/* 登记部门Id */

	@Field
	private String registerDepartmentName;/* 登记部门名称 */

	@Field
	private String registerMan;/* 登记人 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private Date registerDate;/* 登记时间 djsj */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private Date petitionDate;/* 信访日期 xfrq */

	@Field
	private String petitionStatusCode;/* 信访件状态 */

	@Field
	private String addresseeMan;/* 受信人 */

	@Field
	private String attributionAreaCode;/* 问题属地代码 */

	@Field
	private String attributionAreaDetail;/* 问题属地（完整） */

	@Field
	private String contentTypeCode;/* 内容分类代码 */

	@Field
	private String contentTypeLabel;/* 内容分类文本 */

	@Field
	private String purposeCode;/* 信访目的代码 */

	@Field
	private String reasonCode;/* 信访原因代码 */

	@Field
	private String sourceCode;/* 信访来源代码 */

	@Field
	private String firstOfficeName;/* 首次信访机构 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private Date firstDate;/* 首次信访日期 */

	@Field
	private String hotSpotCode;/* 热点问题 rdwt */

	@Field
	private String belongSystemCode;/* 所属系统 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private Date deadlineDate;/* 限办截止时间 */

	@Field
	private String reflectedGradeCode;/* 被反映人级别 */

	@Field
	private String reflectedDuty;/* 被反映人职务 */

	@Field
	private String reflectedAreaCode;/* 被反映人住址代码 */

	@Field
	private String reflectedAreaName;/* 被反映人住址名称 */

	@Field
	private String reflectedObject;/* 被反映人或单位 */

	@Field(type = FieldType.Integer)
	private Integer reviewFlag;/* 复查标志 fcbz */

	@Field(type = FieldType.Integer)
	private Integer recheckFlag;/* 复核标志 fhbz */

	@Field(type = FieldType.Integer)
	private Integer finishFlag;/* 办结标志 bjbz */

	@Field(type = FieldType.Integer)
	private Integer cognizanceFlag;/* 审核认定办结标志 */

	@Field(type = FieldType.Integer)
	private Integer supervisionFlag;/* 是否转督办 */

	@Field(type = FieldType.Integer)
	private Integer evaluateFlag;/* 参与评价 */

	@Field(type = FieldType.Integer)
	private Integer crowdFlag;/* 集体访标识 (1:集体访) */

	@Field(type = FieldType.Integer)
	private Integer specialFlag;/* 是否特殊人员 */

	@Field
	private String specialReason;/* 特殊原因 */

	@Field
	private String letterId;/* 信件条码 */

	@Field
	private String transactWayLabel;/* 办理方式 */

	@Field
	private String transactWayCode;/* 办理方式 */

	@Field
	private String transactOfficeId;/* 办理机构id */

	@Field
	private String transactDepartmentId;/* 办理处室id */

	@Field
	private String transactOfficeName;/* 办理机构名称 */

	@Field
	private String transactDepartmentName;/* 办理处室名称 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private Date transactDate;/* 办理时间 */

	@Field
	private String threatContent;/* 扬言内容 */

	@Field
	private String remarks;/* 备注信息 */

	@Field(type = FieldType.Integer)
	private Integer delFlag;/* 删除标示 */

	@Field
	private String petitionCaseId; /* 信访件id */

	@Field
	private String transactOfficeCode; /* 办理机构代码（bljgdm） */

	@Field
	private String transactOfficeCategory; /* 办理机构类别（bljglb） */

	@Field
	private String transactMen; /* 经办人（jbr） */

	@Field
	private String transactMenId; /* 办理人id */

	@Field
	private String toOfficeId; /* 去向机构id */

	@Field
	private String toOfficeName; /* 去向机构（qxjg） */

	@Field
	private String toOfficeCode; /* 去向机构代码（qxjgdm） */

	@Field
	private String littleToName; /* 小去向名称（xqxmc） */

	@Field
	private String replyNotifyContent; /* 回复告知内容（hfgznr） */

	@Field
	private String transactOpinion; /* 办理意见（blyj） */

	@Field(type = FieldType.Integer)
	private String publishFlag; /* 发布状态 */

	@Field
	private String copyOfficeIds; /* 抄送机构ids(多个) */

	@Field
	private String copyOfficeName; /* 抄送机构(多个) */

	@Field
	private String createBy; /* 创建人 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private String createDate; /* 创建时间 */

	@Field
	private String updateBy; /* 更新者 */

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Field(type = FieldType.Date, format = DateFormat.year_month_day)
	private String updateDate; /* 更新时间 */

	@Field(type = FieldType.Integer)
	private String sort; /* 排序 */
}
