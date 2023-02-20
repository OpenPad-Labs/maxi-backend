package io.maxi.api.config.constant;

/**
 * 会员权益枚举
 */
public enum RightTypeEnum {
    invitationRebate("邀请返利"),
    subDivision("下级分成"),
    teamBonus("团队加成"),
    firstPreferential("首单折扣"),
    holdingAward("持树奖励"),
    otherBenefits("其他福利"),
    ;

    String desc;

    RightTypeEnum(String desc) {
        this.desc = desc;
    }

    public boolean isNeedFormatValue(){
        return this != holdingAward && this != otherBenefits;
    }

    public static RightTypeEnum get(String name){
        for (RightTypeEnum value : RightTypeEnum.values()) {
            if(value.name().equals(name)){
                return value;
            }
        }
        return null;
    }

}
