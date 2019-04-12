create table dbo.DC_CASE_JA_DAY (SBRQ varchar(10) not null, FYDM int not null, AJLX int not null, AJBS varchar(20) not null, AH varchar(50) not null, JARQ varchar(10) not null, JAAY varchar(20) not null, JABDJE money not null, LARQ varchar(10) not null, AJZLX varchar(15) not null, TQXZPC varchar(15) not null) ;
alter table dbo.DC_CASE_JA_DAY add constraint PK_DC_CASE_JA_DAY primary key nonclustered (SBRQ, AJLX, AJBS) ;
create table dbo.DC_CASE_SA_DAY (SBRQ varchar(10) not null, FYDM int not null, AJLX int not null, AJBS varchar(20) not null, AH varchar(50) not null, SARQ varchar(10) not null, LARQ varchar(10) not null, LAAY varchar(20) not null, QSBDJE money not null, AJZLX varchar(15) not null, TQXZPC varchar(15) not null) ;
alter table dbo.DC_CASE_SA_DAY add constraint PK_DC_CASE_SA_DAY primary key nonclustered (SBRQ, AJLX, AJBS) ;

//案件类型代码转换表
CREATE TABLE DMZH_AJLX (
	DM int,
	AJLXMC varchar(30),
	AJLXDM varchar(4),
	SM varchar(50));
INSERT INTO DMZH_AJLX VALUES (
	1,
	'刑事一审',
	'11',
	'');
INSERT INTO DMZH_AJLX VALUES (
	2,
	'刑事二审',
	'12',
	'');
INSERT INTO DMZH_AJLX VALUES (
	3,
	'刑事复核',
	'14',
	'');
INSERT INTO DMZH_AJLX VALUES (
	4,
	'刑事再审',
	'13',
	'');
INSERT INTO DMZH_AJLX VALUES (
	5,
	'刑罚变更',
	'15',
	'');
INSERT INTO DMZH_AJLX VALUES (
	6,
	'民事一审',
	'21',
	'');
INSERT INTO DMZH_AJLX VALUES (
	7,
	'民事二审',
	'22',
	'');
INSERT INTO DMZH_AJLX VALUES (
	8,
	'民事再审',
	'23',
	'');
INSERT INTO DMZH_AJLX VALUES (
	9,
	'民事特殊',
	'24',
	'');
INSERT INTO DMZH_AJLX VALUES (
	10,
	'破产',
	'25',
	'');
INSERT INTO DMZH_AJLX VALUES (
	11,
	'行政一审',
	'31',
	'');
INSERT INTO DMZH_AJLX VALUES (
	12,
	'行政二审',
	'32',
	'');
INSERT INTO DMZH_AJLX VALUES (
	13,
	'行政再审',
	'33',
	'');
INSERT INTO DMZH_AJLX VALUES (
	14,
	'行政非诉执行审查',
	'34',
	'');
INSERT INTO DMZH_AJLX VALUES (
	15,
	'赔偿确认',
	'41',
	'');
INSERT INTO DMZH_AJLX VALUES (
	16,
	'司法赔偿',
	'42',
	'');
INSERT INTO DMZH_AJLX VALUES (
	17,
	'执行',
	'51',
	'');
INSERT INTO DMZH_AJLX VALUES (
	18,
	'再审审查与审判监督',
	'61',
	'');
INSERT INTO DMZH_AJLX VALUES (
	19,
	'诉前保全',
	'81',
	'已弃用，新收的此类案件按非诉保全案件上报');
INSERT INTO DMZH_AJLX VALUES (
	20,
	'信访',
	'71',
	'');
INSERT INTO DMZH_AJLX VALUES (
	21,
	'强制医疗',
	'16',
	'');
INSERT INTO DMZH_AJLX VALUES (
	22,
	'强制医疗复议',
	'17',
	'');
INSERT INTO DMZH_AJLX VALUES (
	23,
	'解除强制医疗',
	'18',
	'');
INSERT INTO DMZH_AJLX VALUES (
	24,
	'纪检监督',
	'91',
	'');
INSERT INTO DMZH_AJLX VALUES (
	25,
	'其他',
	'ZZ',
	'');
INSERT INTO DMZH_AJLX VALUES (
	26,
	'没收违法所得申请审查',
	'1A',
	'');
INSERT INTO DMZH_AJLX VALUES (
	27,
	'没收违法所得二审',
	'1B',
	'');
INSERT INTO DMZH_AJLX VALUES (
	28,
	'没收违法所得再审',
	'1C',
	'');
INSERT INTO DMZH_AJLX VALUES (
	29,
	'强制医疗监督',
	'19',
	'');
INSERT INTO DMZH_AJLX VALUES (
	30,
	'停止执行死刑',
	'1D',
	'');
INSERT INTO DMZH_AJLX VALUES (
	31,
	'刑罚与执行变更监督',
	'1E',
	'');
INSERT INTO DMZH_AJLX VALUES (
	32,
	'刑罚与执行变更备案',
	'1F',
	'');
INSERT INTO DMZH_AJLX VALUES (
	33,
	'其他刑事',
	'1Z',
	'');
INSERT INTO DMZH_AJLX VALUES (
	34,
	'第三人撤销之诉',
	'26',
	'');
INSERT INTO DMZH_AJLX VALUES (
	35,
	'强制清算',
	'27',
	'');
INSERT INTO DMZH_AJLX VALUES (
	36,
	'其他民事',
	'2Z',
	'');
INSERT INTO DMZH_AJLX VALUES (
	37,
	'其他行政',
	'3Z',
	'');
INSERT INTO DMZH_AJLX VALUES (
	38,
	'其他赔偿',
	'4Z',
	'');
INSERT INTO DMZH_AJLX VALUES (
	39,
	'司法救助',
	'A1',
	'');
INSERT INTO DMZH_AJLX VALUES (
	40,
	'其他司法救助',
	'AZ',
	'');
INSERT INTO DMZH_AJLX VALUES (
	41,
	'非诉保全',
	'82',
	'');
INSERT INTO DMZH_AJLX VALUES (
	42,
	'司法制裁',
	'C1',
	'');
INSERT INTO DMZH_AJLX VALUES (
	43,
	'刑事管辖',
	'D1',
	'');
INSERT INTO DMZH_AJLX VALUES (
	44,
	'民事管辖',
	'D2',
	'');
INSERT INTO DMZH_AJLX VALUES (
	45,
	'行政管辖',
	'D3',
	'');
INSERT INTO DMZH_AJLX VALUES (
	46,
	'行政赔偿管辖',
	'D4',
	'');
INSERT INTO DMZH_AJLX VALUES (
	47,
	'其他执行',
	'5Z',
	'');
INSERT INTO DMZH_AJLX VALUES (
	48,
	'人身安全保护令申请审查',
	'28',
	'');
INSERT INTO DMZH_AJLX VALUES (
	49,
	'人身安全保护令变更',
	'29',
	'');
INSERT INTO DMZH_AJLX VALUES (
	50,
	'申请安置教育审查',
	'1G',
	'');
INSERT INTO DMZH_AJLX VALUES (
	51,
	'解除安置教育审查',
	'1H',
	'');
INSERT INTO DMZH_AJLX VALUES (
	52,
	'安置教育复议',
	'1I',
	'');
INSERT INTO DMZH_AJLX VALUES (
	53,
	'安置教育监督',
	'1J',
	'');
INSERT INTO DMZH_AJLX VALUES (
	54,
	'强制清算申请审查',
	'2A',
	'');
INSERT INTO DMZH_AJLX VALUES (
	55,
	'破产申请审查',
	'2B',
	'');
INSERT INTO DMZH_AJLX VALUES (
	56,
	'强制清算上诉',
	'2C',
	'');
INSERT INTO DMZH_AJLX VALUES (
	57,
	'破产上诉',
	'2D',
	'');
INSERT INTO DMZH_AJLX VALUES (
	58,
	'强制清算监督',
	'2E',
	'');
INSERT INTO DMZH_AJLX VALUES (
	59,
	'破产监督',
	'2F',
	'');
INSERT INTO DMZH_AJLX VALUES (
	60,
	'区际认可与执行申请审查',
	'B1',
	'');
INSERT INTO DMZH_AJLX VALUES (
	61,
	'区际送达文书',
	'B2',
	'');
INSERT INTO DMZH_AJLX VALUES (
	62,
	'区际调查取证',
	'B3',
	'');
INSERT INTO DMZH_AJLX VALUES (
	63,
	'区际被判刑人移管',
	'B4',
	'');
INSERT INTO DMZH_AJLX VALUES (
	64,
	'区际罪赃移交',
	'B5',
	'');
INSERT INTO DMZH_AJLX VALUES (
	65,
	'国际承认与执行申请审查',
	'B6',
	'');
INSERT INTO DMZH_AJLX VALUES (
	66,
	'国际送达文书',
	'B7',
	'');
INSERT INTO DMZH_AJLX VALUES (
	67,
	'国际调查取证',
	'B8',
	'');
INSERT INTO DMZH_AJLX VALUES (
	68,
	'国际被判刑人移管',
	'B9',
	'');
INSERT INTO DMZH_AJLX VALUES (
	69,
	'国际引渡',
	'BA',
	'');
INSERT INTO DMZH_AJLX VALUES (
	70,
	'行政赔偿一审',
	'43',
	'');
INSERT INTO DMZH_AJLX VALUES (
	71,
	'行政赔偿二审',
	'44',
	'');
INSERT INTO DMZH_AJLX VALUES (
	72,
	'行政赔偿依职权再审审查',
	'45',
	'');
INSERT INTO DMZH_AJLX VALUES (
	73,
	'行政赔偿申请再审审查',
	'46',
	'');
INSERT INTO DMZH_AJLX VALUES (
	74,
	'行政赔偿抗诉再审审查',
	'47',
	'');
INSERT INTO DMZH_AJLX VALUES (
	75,
	'行政赔偿再审',
	'48',
	'');
INSERT INTO DMZH_AJLX VALUES (
	76,
	'其他行政赔偿',
	'4Z',
	'');
