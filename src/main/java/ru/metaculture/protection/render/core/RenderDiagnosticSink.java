package ru.metaculture.protection;

public interface RenderDiagnosticSink {
   int compute();

   void invoke(RenderHashSink renderHashSink);
}
