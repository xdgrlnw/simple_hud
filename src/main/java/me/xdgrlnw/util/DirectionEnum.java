package me.xdgrlnw.util;

public enum DirectionEnum {

	N("North"), NE("North-east"), E("East"), SE("South-east"), S("South"), SW("South-west"), W("West"),
	NW("North-west");

	public final String longName;

	DirectionEnum(String longName) {
		this.longName = longName;
	}

	public static DirectionEnum getByYawDegrees(float degrees) {
		degrees += 180;
        return switch (Math.round(degrees / 45)) {
            case 0 -> N;
            case 1 -> NE;
            case 2 -> E;
            case 3 -> SE;
            case 4 -> S;
            case 5 -> SW;
            case 6 -> W;
            case 7 -> NW;
            // case 8: return N;
            default -> N;
        };
	}
	
	public String getX() {
		if (DirectionEnum.E.equals(this)) {
			return " ++";
		} else if (DirectionEnum.NE.equals(this)) {
			return " +";
		} else if (DirectionEnum.SE.equals(this)) {
			return " +";
		} else if (DirectionEnum.W.equals(this)) {
			return " --";
		} else if (DirectionEnum.NW.equals(this)) {
			return " -";
		} else if (DirectionEnum.SW.equals(this)) {
			return " -";
		} else {
			return "";
		}
	}

	public String getZ() {
		if (DirectionEnum.S.equals(this)) {
			return " ++";
		} else if (DirectionEnum.SE.equals(this)) {
			return " +";
		} else if (DirectionEnum.SW.equals(this)) {
			return " +";
		} else if (DirectionEnum.N.equals(this)) {
			return " --";
		} else if (DirectionEnum.NE.equals(this)) {
			return " -";
		} else if (DirectionEnum.NW.equals(this)) {
			return " -";
		} else {
			return "";
		}
	}
}