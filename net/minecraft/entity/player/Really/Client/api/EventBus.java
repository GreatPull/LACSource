package net.minecraft.entity.player.Really.Client.api;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.utils.Helper;

public class EventBus {
   private ConcurrentHashMap registry = new ConcurrentHashMap();
   private final Comparator comparator = (h, h1) -> {
      return Byte.compare(Handler.access$0((Handler) h), Handler.access$0((Handler) h1));
   };
   private final Lookup lookup = MethodHandles.lookup();
   private static final EventBus instance = new EventBus();

   public static EventBus getInstance() {
      return instance;
   }

   public void register(Object... objs) {
      Object[] arrobject = objs;
      int n = objs.length;

      for(int n2 = 0; n2 < n; ++n2) {
         Object obj = arrobject[n2];
         Method[] arrmethod = obj.getClass().getDeclaredMethods();
         int n3 = arrmethod.length;

         for(int n4 = 0; n4 < n3; ++n4) {
            Method m = arrmethod[n4];
            if(m.getParameterCount() == 1 && m.isAnnotationPresent(EventHandler.class)) {
               Class eventClass = m.getParameterTypes()[0];
               if(!this.registry.containsKey(eventClass)) {
                  this.registry.put(eventClass, new CopyOnWriteArrayList());
               }

               ((List)this.registry.get(eventClass)).add(new Handler(this, m, obj, ((EventHandler)m.getDeclaredAnnotation(EventHandler.class)).priority()));
               ((List)this.registry.get(eventClass)).sort(this.comparator);
            }
         }
      }

   }

   public void unregister(Object... objs) {
      Object[] arrobject = objs;
      int n = objs.length;

      for(int n2 = 0; n2 < n; ++n2) {
         Object obj = arrobject[n2];
         Iterator var7 = this.registry.values().iterator();

         while(var7.hasNext()) {
            List list = (List)var7.next();
            Iterator var9 = list.iterator();

            while(var9.hasNext()) {
               Handler data = (Handler)var9.next();
               if(Handler.access$1(data) == obj) {
                  list.remove(data);
               }
            }
         }
      }

   }

   public Event call(Event event) {
      boolean whiteListedEvents = event instanceof EventTick || event instanceof EventPreUpdate || event instanceof EventPostUpdate;
      List list = (List)this.registry.get(event.getClass());
      if(list != null && !list.isEmpty()) {
         Iterator var5 = list.iterator();

         while(var5.hasNext()) {
            Handler data = (Handler)var5.next();

            try {
               if(list instanceof Module) {
                  if(((Module)list).isEnabled()) {
                     if(whiteListedEvents) {
                        Helper.mc.mcProfiler.startSection(((Module)list).getName());
                     }

                     if(whiteListedEvents) {
                        Helper.mc.mcProfiler.endSection();
                     }
                  }
               } else {
                  if(whiteListedEvents) {
                     Helper.mc.mcProfiler.startSection("non module");
                  }

                  if(whiteListedEvents) {
                     Helper.mc.mcProfiler.endSection();
                  }
               }

               Handler.access$2(data).invokeExact(Handler.access$1(data), event);
            } catch (Throwable var7) {
               var7.printStackTrace();
            }
         }
      }

      return event;
   }

   static Lookup access$0(EventBus var0) {
      return var0.lookup;
   }
}


class Handler {
   private MethodHandle handler;
   private Object parent;
   private byte priority;
   final EventBus this$0;

   public Handler(EventBus var1, Method method, Object parent, byte priority) {
      this.this$0 = var1;
      if(!method.isAccessible()) {
         method.setAccessible(true);
      }

      MethodHandle m = null;

      try {
         m = EventBus.access$0(var1).unreflect(method);
      } catch (IllegalAccessException var7) {
         var7.printStackTrace();
      }

      if(m != null) {
         this.handler = m.asType(m.type().changeParameterType(0, Object.class).changeParameterType(1, Event.class));
      }

      this.parent = parent;
      this.priority = priority;
   }

   static byte access$0(Handler var0) {
      return var0.priority;
   }

   static Object access$1(Handler var0) {
      return var0.parent;
   }

   static MethodHandle access$2(Handler var0) {
      return var0.handler;
   }
}

