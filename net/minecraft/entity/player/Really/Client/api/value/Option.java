package net.minecraft.entity.player.Really.Client.api.value;

import net.minecraft.entity.player.Really.Client.api.value.Value;

public class Option extends Value {
   public int anim;

   public Option(String displayName, String name, Object enabled) {
      super(displayName, name);
      this.setValue(enabled);
   }
}
