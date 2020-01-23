package net.minecraft.entity.player.Really.Client.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.ui.login.Alt;
import net.minecraft.entity.player.Really.Client.ui.login.AltManager;

public class FileManager {
   private static File dir;
   private static final File ALT = getConfigFile("Alts");
   private static final File LASTALT = getConfigFile("LastAlt");

   static {
      File mcDataDir = Minecraft.getMinecraft().mcDataDir;
      Client.instance.getClass();
      dir = new File(mcDataDir, "LAC");
   }

   public static void loadLastAlt() {
      try {
         if(!LASTALT.exists()) {
            PrintWriter printWriter = new PrintWriter(new FileWriter(LASTALT));
            printWriter.println();
            printWriter.close();
         } else if(LASTALT.exists()) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LASTALT));

            String s;
            while((s = bufferedReader.readLine()) != null) {
               if(s.contains("\t")) {
                  s = s.replace("\t", "    ");
               }

               if(s.contains("    ")) {
                  String[] parts = s.split("    ");
                  String[] account = parts[1].split(":");
                  if(account.length == 2) {
                     Client.instance.getAltManager().setLastAlt(new Alt(account[0], account[1], parts[0]));
                  } else {
                     String pw = account[1];

                     for(int i = 2; i < account.length; ++i) {
                        pw = String.valueOf(pw) + ":" + account[i];
                     }

                     Client.instance.getAltManager().setLastAlt(new Alt(account[0], pw, parts[0]));
                  }
               } else {
                  String[] account2 = s.split(":");
                  if(account2.length == 1) {
                     Client.instance.getAltManager().setLastAlt(new Alt(account2[0], ""));
                  } else if(account2.length == 2) {
                     Client.instance.getAltManager().setLastAlt(new Alt(account2[0], account2[1]));
                  } else {
                     String pw2 = account2[1];

                     for(int j = 2; j < account2.length; ++j) {
                        pw2 = String.valueOf(pw2) + ":" + account2[j];
                     }

                     Client.instance.getAltManager().setLastAlt(new Alt(account2[0], pw2));
                  }
               }
            }

            bufferedReader.close();
         }
      } catch (FileNotFoundException var6) {
         var6.printStackTrace();
      } catch (IOException var7) {
         var7.printStackTrace();
      }

   }

   public static void saveLastAlt() {
      try {
         PrintWriter printWriter = new PrintWriter(LASTALT);
         Alt alt = Client.instance.getAltManager().getLastAlt();
         if(alt != null) {
            if(alt.getMask().equals("")) {
               printWriter.println(String.valueOf(alt.getUsername()) + ":" + alt.getPassword());
            } else {
               printWriter.println(String.valueOf(alt.getMask()) + "    " + alt.getUsername() + ":" + alt.getPassword());
            }
         }

         printWriter.close();
      } catch (FileNotFoundException var2) {
         var2.printStackTrace();
      }

   }

   public static void loadAlts() {
      try {
         BufferedReader bufferedReader = new BufferedReader(new FileReader(ALT));
         if(!ALT.exists()) {
            PrintWriter printWriter = new PrintWriter(new FileWriter(ALT));
            printWriter.println();
            printWriter.close();
         } else {
            String s;
            if(ALT.exists()) {
               while((s = bufferedReader.readLine()) != null) {
                  if(s.contains("\t")) {
                     s = s.replace("\t", "    ");
                  }

                  if(s.contains("    ")) {
                     String[] parts = s.split("    ");
                     String[] account = parts[1].split(":");
                     if(account.length == 2) {
                        Client.instance.getAltManager();
                        AltManager.getAlts().add(new Alt(account[0], account[1], parts[0]));
                     } else {
                        String pw = account[1];

                        for(int i = 2; i < account.length; ++i) {
                           pw = String.valueOf(pw) + ":" + account[i];
                        }

                        Client.instance.getAltManager();
                        AltManager.getAlts().add(new Alt(account[0], pw, parts[0]));
                     }
                  } else {
                     String[] account2 = s.split(":");
                     if(account2.length == 1) {
                        Client.instance.getAltManager();
                        AltManager.getAlts().add(new Alt(account2[0], ""));
                     } else if(account2.length == 2) {
                        try {
                           Client.instance.getAltManager();
                           AltManager.getAlts().add(new Alt(account2[0], account2[1]));
                        } catch (Exception var6) {
                           var6.printStackTrace();
                        }
                     } else {
                        String pw2 = account2[1];

                        for(int j = 2; j < account2.length; ++j) {
                           pw2 = String.valueOf(pw2) + ":" + account2[j];
                        }

                        Client.instance.getAltManager();
                        AltManager.getAlts().add(new Alt(account2[0], pw2));
                     }
                  }
               }
            }
         }

         bufferedReader.close();
      } catch (Exception var7) {
         ;
      }

   }

   public static void saveAlts() {
      try {
         System.out.println("skrt");
         PrintWriter printWriter = new PrintWriter(ALT);
         Client.instance.getAltManager();

         for(Alt alt : AltManager.getAlts()) {
            if(alt.getMask().equals("")) {
               printWriter.println(String.valueOf(alt.getUsername()) + ":" + alt.getPassword());
            } else {
               printWriter.println(String.valueOf(alt.getMask()) + "    " + alt.getUsername() + ":" + alt.getPassword());
            }
         }

         printWriter.close();
      } catch (FileNotFoundException var3) {
         var3.printStackTrace();
      }

   }

   public static File getConfigFile(String name) {
      File file = new File(dir, String.format("%s.txt", new Object[]{name}));
      if(!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var3) {
            ;
         }
      }

      return file;
   }

   public static void init() {
      if(!dir.exists()) {
         dir.mkdir();
      }

      loadLastAlt();
      loadAlts();
   }

   public static List<String> read(String file) {
      List<String> out = new ArrayList();

      try {
         if(!dir.exists()) {
            dir.mkdir();
         }

         File f = new File(dir, file);
         if(!f.exists()) {
            f.createNewFile();
         }

         Throwable t = null;

         Object var12;
         try {
            FileInputStream fis = new FileInputStream(f);

            try {
               InputStreamReader isr = new InputStreamReader(fis);

               try {
                  BufferedReader br = new BufferedReader(isr);

                  try {
                     String line = "";

                     while((line = br.readLine()) != null) {
                        out.add(line);
                     }
                  } finally {
                     if(br != null) {
                        br.close();
                     }

                  }

                  if(isr != null) {
                     isr.close();
                  }
               } finally {
                  if(t == null) {
                     Throwable t2 = null;
                     t = t2;
                  } else {
                     Throwable t2 = null;
                     if(t != t2) {
                        t.addSuppressed(t2);
                     }
                  }

                  if(isr != null) {
                     isr.close();
                  }

               }

               if(fis == null) {
                  return out;
               }

               fis.close();
               var12 = out;
            } finally {
               if(t == null) {
                  Throwable t3 = null;
                  t = t3;
               } else {
                  Throwable t3 = null;
                  if(t != t3) {
                     t.addSuppressed(t3);
                  }
               }

               if(fis != null) {
                  fis.close();
               }

            }
         } finally {
            if(t == null) {
               Throwable t4 = null;
            } else {
               Throwable t4 = null;
               if(t != t4) {
                  t.addSuppressed(t4);
               }
            }

         }

         return (List)var12;
      } catch (IOException var40) {
         var40.printStackTrace();
         return out;
      }
   }

   public static void save(String file, String content, boolean append) {
      try {
         File f = new File(dir, file);
         if(!f.exists()) {
            f.createNewFile();
         }

         Throwable t = null;

         try {
            FileWriter writer = new FileWriter(f, append);

            try {
               writer.write(content);
            } finally {
               if(writer != null) {
                  writer.close();
               }

            }
         } finally {
            if(t == null) {
               Throwable t2 = null;
            } else {
               Throwable t2 = null;
               if(t != t2) {
                  t.addSuppressed(t2);
               }
            }

         }
      } catch (IOException var17) {
         var17.printStackTrace();
      }

   }
}
