package me.tuhin47.exporter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tuhin47.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public interface ExporterDTO {

    @JsonIgnore
    default List<String> getHeaders() {
        PriorityQueue<Element> priorityQueue = getPriorityElements();
        return priorityQueue.stream().map(Element::getValue).collect(Collectors.toList());
    }

    @JsonIgnore
    default List<Field> getFields() {
        PriorityQueue<Element> priorityQueue = getPriorityElements();
        return priorityQueue.stream().map(Element::getField).collect(Collectors.toList());
    }

    private PriorityQueue<Element> getPriorityElements() {
        PriorityQueue<Element> priorityQueue = new PriorityQueue<>();
        Field[] declaredFields = getClass().getDeclaredFields();
        for (int i = 0, declaredFieldsLength = declaredFields.length; i < declaredFieldsLength; i++) {
            Field field = declaredFields[i];
            float priority = i;
            var name = StringUtils.camelCaseToSentence(field.getName());

            if (field.isAnnotationPresent(ExportColumn.class)) {
                var annotation = field.getAnnotation(ExportColumn.class);
                var sortOrder = annotation.sortOrder();
                priority = (sortOrder == -1 ? i : (float) (sortOrder - 0.5));
                name = annotation.value();
            }

            priorityQueue.add(new Element(priority, name, field));
        }
        return priorityQueue;
    }
}

@Getter
@AllArgsConstructor
class Element implements Comparable<Element> {
    private final float priority;
    private final String value;
    private final Field field;

    @Override
    public int compareTo(Element other) {
        return Float.compare(this.priority, other.priority);
    }
}