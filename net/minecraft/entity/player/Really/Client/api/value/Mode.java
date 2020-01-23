package net.minecraft.entity.player.Really.Client.api.value;

import net.minecraft.entity.player.Really.Client.api.value.Value;

public class Mode extends Value {
   private Enum[] modes;

   public Mode(String displayName, String name, Enum[] modes, Enum value) {
      super(displayName, name);
      this.modes = modes;
      this.setValue(value);
   }

   public Enum[] getModes() {
      return this.modes;
   }

   public String getModeAsString() {
      return ((Enum)this.getValue()).name();
   }

   public void setMode(String mode) {
      for(Enum e : this.modes) {
         if(e.name().equalsIgnoreCase(mode)) {
            this.setValue(e);
         }
      }

   }

   public boolean isValid(String name) {
      for(Enum e : this.modes) {
         if(e.name().equalsIgnoreCase(name)) {
            return true;
         }
      }

      return false;
   }
}
