package ru.metaculture.sdk;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Obfuscate {
   Obfuscate.Preset preset() default Obfuscate.Preset.DEFAULT;

   boolean const_flow() default false;

   Obfuscate.ConstFlow const_flow_type() default Obfuscate.ConstFlow.MEDIUM;

   boolean control_flow() default false;

   Obfuscate.ControlFlow control_flow_type() default Obfuscate.ControlFlow.MIXED;

   boolean string_encrypt() default false;

   Obfuscate.StringEncrypt string_encrypt_type() default Obfuscate.StringEncrypt.XOR;

   boolean number_encrypt() default false;

   Obfuscate.NumberEncrypt number_encrypt_type() default Obfuscate.NumberEncrypt.LIGHT;

   boolean invoke_dynamic() default false;

   boolean hide_reflection() default false;

   boolean dead_code() default false;

   Obfuscate.DeadCode dead_code_type() default Obfuscate.DeadCode.LIGHT;

   boolean junk_code() default false;

   boolean strip_debug() default true;

   boolean watermark() default false;

   String watermark_text() default "";

   public static enum ConstFlow {
      LIGHT,
      MEDIUM,
      HEAVY;
   }

   public static enum ControlFlow {
      SPLIT,
      FLAT,
      MIXED;
   }

   public static enum DeadCode {
      LIGHT,
      MEDIUM,
      HEAVY;
   }

   public static enum NumberEncrypt {
      LIGHT,
      HEAVY;
   }

   public static enum Preset {
      DEFAULT,
      LIGHT,
      MEDIUM,
      HEAVY,
      EXTREME;
   }

   public static enum StringEncrypt {
      XOR,
      AES,
      RC4,
      SHACAL2;
   }
}
