package me.tuhin47.exporter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class CSVGenerator<T extends ExporterDTO> implements DataExporter<T> {

    @Override
    public byte[] generate(List<? extends T> listRecords) {
        var outputStream = new ByteArrayOutputStream();
        var pw = new PrintWriter(outputStream);
        var headers = getHeaders(listRecords);
        pw.println(String.join(",", headers));
        var fieldFunctionTreeMap = getFunctions(listRecords);
        for (T val : listRecords) {
            String data = fieldFunctionTreeMap.stream().map(objectFunction -> objectFunction.apply(val).toString()).collect(Collectors.joining(","));
            pw.println(data);
        }
        pw.flush();
        pw.close();

        return outputStream.toByteArray();
    }

    @Override
    public String getExtension() {
        return ".csv";
    }
}