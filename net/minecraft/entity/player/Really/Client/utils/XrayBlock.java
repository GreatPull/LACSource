package net.minecraft.entity.player.Really.Client.utils;

public class XrayBlock {
   public double x;
   public double y;
   public double z;
   public String type;

   public XrayBlock(double a, double a2, double a3, String xx) {
      this.z = a;
      this.y = a2;
      this.x = a3;
      this.type = xx;
   }

   public XrayBlock() {
      this.x = 0.0D;
      this.y = 0.0D;
      this.z = 0.0D;
   }
}
