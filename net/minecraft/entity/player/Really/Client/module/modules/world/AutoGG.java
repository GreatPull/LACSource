package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventChat;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.world.AutoL;

public class AutoGG extends Module {
   private Mode mode = new Mode("Mode", "mode", AutoGG.AutoPlayMode.values(), AutoGG.AutoPlayMode.SkyWarsSoloNormal);
   public static Option Suffix = new Option("Suffix", "Suffix", Boolean.valueOf(true));
   public static Option AutoPlay = new Option("AutoPlay", "AutoPlay", Boolean.valueOf(false));
   public static Option LHanabi = new Option("LHanabi", "LHanabi", Boolean.valueOf(true));
   String[] LHanabiS = new String[]{"你又要使用你那破破烂烂的Hanabi击杀你的爸爸？", "拿你父母血汗钱买你性格恶劣Margele的耀武扬威Hanabi客户端，不如他妈多给你父母攒点钱买点好的棺材。", "不要再使用你拿破破烂烂自欺欺人的废物Hanabi客户端来和你爸爸对刀了。", "天天一把屎一把尿的舔你废物Margele和Ax，还白嫖NEL和Hanabi废物客户端在这里和爷玩飞行金苹果持久战？", "看看你的垃圾Hanabi再看看爷的客户端是不是差远了？", "为什么要用你的fwHanabi畏惧你爷爷我？", "我说Margele不要想着靠着让你给你支持，我也不说这狗崽子开着Hanabi实在不知耻", "首先感觉Margele的实力和我懂得都不太高，所以感觉我的个子只能到你的腰，别跟我说你的膝盖插飞镖你看你的脸长得就像加菲猫。", "不想说Margele这个傻逼长得太胖,所以感觉他的Hanabi写的实在太糟，这几轮的solo总是那么文明，是不是玩hypixel还要带上大学文凭？", "我说AX和Margele，什么是嘴臭，他来自你的心里，不懂的尊重的人我根本不留情。", "我说兄弟，他妈是什么废物，给我三千城管我能削了地球，在这之前我一个拳头就能打爆Hanabi的头。", "我已我现在可以把Margele杀掉，你长得就像过年放的擦炮。", "我是弟弟Margele，Hanabi全都是抄袭，字符串，天天拿着这个那个权限耀武扬威，购买Hanabi的都是蠢货哈哈。", "我是中国第一最有能力的拷贝大师李佳乐，AW太辣鸡了转让给了镜子，Hanabi才是真真正正的圈钱客户端，只要线下约我见面就送源码，这是我领快递的地方，欢迎寄炸弹给我：山东省滨州市滨城区彭李街道黄河十路文化街二十里堡小区西区云柜服务站", "我是带神Margele，最近和镜子搞得太无聊了，想换换口味啊~欢迎拨打我的电话：13356283955，和我成为一日八次的人吧~", "瞧瞧你这个废物李佳乐，你父亲李英琦被我踩在脚底蹭地板，你的母亲崔琳博被我当做奴隶擦地毯。 ", "快点用你的废物Hanabi杀了你的爸妈，让他们瞧瞧有多么废，就像你和镜子像个gay。", "为什么Margele和我battle，我一下子单杀你还要双杀你的父母。", "对你没有意思，对你没有攻击，打败你的废物客户端才是我最真实的声音。", "看看Margele的考试成绩，还在写代码，写你马？那么窝囊像个败家子，为什么不像范杨孝当个带孝子？", "Margele你是山东人还是他妈来自东三省？用你Hanabi还是我" + Client.ClientName + "把你整？", "我说Margele为什么出我电话还是个空号？难道你吃毒品当做保健品一定要把它服掉？", "Margele他妈这里他妈真牛逼，抄了我的源码反编译以后真垃圾。", "为什么Margele是个败家子？因为他曾经吧范杨孝给笑死！", "哇塞为什么Margele一直要卖睾丸？原来他有一支崂山性病代表团！", "我说Margele为什么那么像偶像练习生？因为他的本名叫做李佳乐称作蔡徐坤。", "天天对着电脑扣字对范杨孝进行攻击，你懂吗Margele，不是我吹你也不是我黑你，你的端是真的他妈“牛逼”我才来操你。", "说我窝囊废你他妈是个什么东西？我初一有能力你又有什么资格在这说的淅淅沥沥？"};

   public AutoGG() {
      super("AutoGG", new String[]{"AutoGG"}, ModuleType.World);
      this.addValues(new Value[]{Suffix, LHanabi, AutoPlay, this.mode});
   }

   @EventHandler
   private void onChat(EventChat e) {
      if(!e.getMessage().toLowerCase().contains("a kick occurred in your connection") && !e.getMessage().contains("你的网络连接出现小问题")) {
         if(e.getMessage().contains("Winner") || e.getMessage().contains("胜利者")) {
            if(!((Boolean)AutoL.nodelay.getValue()).booleanValue()) {
               if(!AutoL.timer.hasReached(3200.0D)) {
                  return;
               }

               AutoL.timer.reset();
            }

            if(((Boolean)Suffix.getValue()).booleanValue()) {
               if(((Boolean)LHanabi.getValue()).booleanValue()) {
                  Random r2 = new Random();
                  Minecraft.thePlayer.sendChatMessage("[" + Client.ClientName + "]" + this.LHanabiS[r2.nextInt(28)]);
               } else {
                  Minecraft.thePlayer.sendChatMessage("[" + Client.ClientName + "]GG");
               }
            } else if(((Boolean)LHanabi.getValue()).booleanValue()) {
               Random r2 = new Random();
               Minecraft.thePlayer.sendChatMessage(this.LHanabiS[r2.nextInt(28)]);
            } else {
               Minecraft.thePlayer.sendChatMessage("GG");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.Bedwars1v1) {
               Minecraft.thePlayer.sendChatMessage("/play bedwars_eight_one");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.Bedwars2v2) {
               Minecraft.thePlayer.sendChatMessage("/play bedwars_eight_two");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.Bedwars3v3) {
               Minecraft.thePlayer.sendChatMessage("/play bedwars_four_three");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.Bedwars4v4) {
               Minecraft.thePlayer.sendChatMessage("/play bedwars_four_four");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.SkyWarsSoloNormal) {
               Minecraft.thePlayer.sendChatMessage("/play solo_normal");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.SkyWarsSoloInsane) {
               Minecraft.thePlayer.sendChatMessage("/play solo_insane");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.SkyWarsTeamsNormal) {
               Minecraft.thePlayer.sendChatMessage("/play teams_normal");
            }

            if(this.mode.getValue() == AutoGG.AutoPlayMode.SkyWarsTeamsInsane) {
               Minecraft.thePlayer.sendChatMessage("/play teams_insane");
            }
         }

      } else {
         Minecraft.thePlayer.sendChatMessage("/back");
      }
   }

   static enum AutoPlayMode {
      Bedwars4v4,
      Bedwars2v2,
      Bedwars3v3,
      Bedwars1v1,
      SkyWarsSoloNormal,
      SkyWarsSoloInsane,
      SkyWarsTeamsNormal,
      SkyWarsTeamsInsane;
   }
}
