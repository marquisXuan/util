package org.yyx.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 用于导入员工数据的实体
 * Created by 胡友云 on 2018/1/26 - 9:28
 * Concat hanyihuyouyun@163.com
 */
@Data
public class ImportEmployee implements Serializable {
    private static final long serialVersionUID = 5959391615971387943L;
    /**
     * 员工信息表主键标识
     */
    private Integer pkField;
    /**
     * 员工姓名
     */
    private String employeeName;
    /**
     * 员工头像图片路径
     */
    private String headerImg;
    /**
     * 员工的工号
     */
    private String jobNumber;
    /**
     * 意向职位
     */
    private String meaningPositionName;
    /**
     * 员工的岗位
     */
    private String postName;
    /**
     * 员工的岗位级别
     */
    private Byte postLevel;
    /**
     * 员工性别
     */
    private Byte employeeGender;
    /**
     * 员工入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;
    /**
     * 员工离职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date quitDate;
    /**
     * 员工试用期
     */
    private String probationPeriodDate;
    /**
     * 员工实际转正日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actualChangeEmployeeDate;
    /**
     * 员工预计转正日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date aboutChangeEmployeeDate;
    /**
     * 员工考勤编号
     */
    private String checkWorkAttendance;
    /*
    员工：
	是否离职状态：
		2.待入职
		3.试用
		4.正式
		5.待离职
		1.离职
	工作性质：
		1.全职
		2.兼职
		3.实习
     */
    /**
     * 是否离职状态
     */
    private Byte isQuit;
    /**
     * 招聘渠道
     */
    private Byte recruitChannel;
    /**
     * 档案编号
     */
    private String documentId;
    /**
     * 工作城市
     */
    private String workCity;
    /**
     * 首次工作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date firstWorkDate;
    /**
     * 工作性质
     */
    private Byte workNature;
    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    /**
     * 记录创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gmtCreate;
    /**
     * 最后一次修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date gmtModified;
    /**
     * 合同号
     */
    private String contractNumber;
    /**
     * 合同生效时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractEffectTime;
    /**
     * 合同失效时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contractLoseTime;
    /**
     * 居住地址
     */
    private String residentialAddress;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 个人邮箱
     */
    private String personalMailbox;
    /**
     * 工作电话
     */
    private String workPhone;
    /**
     * 分机号
     */
    private String extensionNumber;
    /**
     * 工作邮箱
     */
    private String workMailbox;
    /**
     * QQ号
     */
    private String qqNumber;
    /**
     * 微信号
     */
    private String weChatNumber;
    /**
     * 离职原因
     */
    private String reasonsLeaving;
    /**
     * 英文名
     */
    private String englishName;
    /**
     * 合同公司
     */
    private String contractCompany;
    /**
     * 工作地点
     */
    private String workingPlace;
    /**
     * 预计入职时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedEntryTime;
    /**
     * 个人社保账号
     */
    private Integer socialSecurityAccount;
    /**
     * 个人公积金账号
     */
    private Integer providentFundAccount;
    /**
     * 工资卡卡号
     */
    private String payrollCardNumber;
    /**
     * 工资卡开户行
     */
    private String payrollCardBank;
    /**
     * 工资卡开户城市
     */
    private String payrollCardOpenCity;
    /**
     * 户籍城市
     */
    private String householdRegistrationCity;
    /**
     * 证件类型
     */
    private String documentType;
    /**
     * 证件号码
     */
    private String identificationNumber;
    /**
     * 户口性质
     */
    private String natureOfHouseholdRegistration;
    /**
     * 身份证有效期
     */
    private String validPeriodOfIdCard;
    /**
     * 民族
     */
    private String nation;
    /**
     * 籍贯
     */
    private String placeOfOrigin;
    /**
     * 政治面貌
     */
    private String politicalVisage;
    /**
     * 是否已婚
     */
    private String marriedOrNot;
    /**
     * 是否已育
     */
    private String fertileOrNot;
    /**
     * 血型
     */
    private String bloodType;
    /**
     * 部门Id
     */
    private Integer departmentId;
    /**
     * 职位Id
     */
    private Integer positionId;
}
