package ru.metaculture.protection;

@FunctionalInterface
public interface ShaderNodeEmitter {
   String emit(ShaderExpressionUtils shaderExpressionUtils, ShaderNodeKind shaderNodeKind, String string);
}
