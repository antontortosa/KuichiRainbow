package github.antontortosa.kuichi

enum class StateEnum {
    AL,AK,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,
    KS,KY,LA,ME,MD,MA,MI,MN,MS,MO,MT,NE,NV,NH,NJ,
    NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,
    VA,WA,WV,WI,WY
}

enum class ColorEnum (val rgb: Int){
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

enum class JewelType{
    Earrings,Necklace
}

enum class AccessoryType{
    HairBand, Belt, Clips
}

/*
* EXTENSIONS
* */

//fun ClientDto.fullName():String = "${this.name} ${this.surname}"