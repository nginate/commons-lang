package com.github.nginate.commons.lang;

import com.google.common.collect.Sets;
import org.assertj.core.util.Maps;
import org.junit.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.nginate.commons.lang.NCollections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NCollectionsTest {

    @Test
    public void testGetFirstNonNull() throws Exception {
        Integer value = 1;
        List<Integer> list = Collections.singletonList(value);
        assertThat(getFirst(list)).isNotNull().isEqualTo(value);
    }

    @Test
    public void testGetFirstFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(getFirst(list)).isNull();
    }

    @Test
    public void testGetFirstFromNull() throws Exception {
        List<Integer> list = null;
        assertThatThrownBy(() ->  getFirst(list)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testGetFirstOptionalNonEmpty() throws Exception {
        Integer value = 1;
        List<Integer> list = Collections.singletonList(value);
        assertThat(getFirstOptional(list)).isNotEmpty().contains(value);
    }

    @Test
    public void testGetFirstOptionalFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(getFirstOptional(list)).isEmpty();
    }

    @Test
    public void testGetFirstOptionalFromNull() throws Exception {
        List<Integer> list = null;
        assertThatThrownBy(() ->  getFirstOptional(list)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testRequireFirst() throws Exception {
        Integer value = 1;
        List<Integer> list = Collections.singletonList(value);
        assertThat(requireFirst(list)).isNotNull().isEqualTo(value);
    }

    @Test
    public void testRequireFirstFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThatThrownBy(() -> requireFirst(list)).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testRequireFirstFromNull() throws Exception {
        List<Integer> list = null;
        assertThatThrownBy(() -> requireFirst(list)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testGetSingle() throws Exception {
        Integer value = 1;
        List<Integer> list = Collections.singletonList(value);
        assertThat(getSingle(list)).isNotNull().isEqualTo(value);
    }

    @Test
    public void testGetSingleFromNonSingleValued() throws Exception {
        List<Integer> list = Arrays.asList(1, 2);
        assertThatThrownBy(() -> getSingle(list)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetSingleFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(getSingle(list)).isNull();
    }

    @Test
    public void testGetSingleFromNull() throws Exception {
        List<Integer> list = null;
        assertThatThrownBy(() -> getSingle(list)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testGetSingleOptional() throws Exception {
        Integer value = 1;
        List<Integer> list = Collections.singletonList(value);
        assertThat(getSingleOptional(list)).isNotEmpty().contains(value);
    }

    @Test
    public void testGetSingleOptionalFromNonSingleValued() throws Exception {
        List<Integer> list = Arrays.asList(1, 2);
        assertThatThrownBy(() -> getSingleOptional(list)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetSingleOptionalFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(getSingleOptional(list)).isEmpty();
    }

    @Test
    public void testGetSingleOptionalFromNull() throws Exception {
        List<Integer> list = null;
        assertThatThrownBy(() ->  getSingleOptional(list)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testRequireSingle() throws Exception {
        Integer value = 1;
        List<Integer> list = Collections.singletonList(value);
        assertThat(requireSingle(list)).isNotNull().isEqualTo(value);
    }

    @Test
    public void testGetRequireSingleFromNonSingleValued() throws Exception {
        List<Integer> list = Arrays.asList(1, 2);
        assertThatThrownBy(() -> requireSingle(list)).isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testRequireSingleFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThatThrownBy(() -> requireSingle(list)).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testRequireSingleFromNull() throws Exception {
        List<Integer> list = null;
        assertThatThrownBy(() -> requireSingle(list)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testMapToArrayFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(mapToArray(list, Integer[]::new)).isNotNull().isEmpty();
    }

    @Test
    public void testMapToArrayFromNonEmpty() throws Exception {
        List<Integer> list = Arrays.asList(1, 2);
        assertThat(mapToArray(list, Integer[]::new)).isNotNull().hasSameSizeAs(list).containsExactlyElementsOf(list);
    }

    @Test
    public void testMapToArrayFromNull() throws Exception {
        assertThat(mapToArray(null, Integer[]::new)).isNull();
    }

    @Test
    public void testMapToArrayWithNullGenerator() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThatThrownBy(() -> mapToArray(list, null)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testMapToArrayFromEmptyWithSameTypeMapper() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(mapToArray(list, Integer[]::new, Function.identity())).isNotNull().isEmpty();
    }

    @Test
    public void testMapToArrayFromNonEmptyWithSameTypeMapper() throws Exception {
        List<Integer> list = Arrays.asList(1, 2);
        assertThat(mapToArray(list, Integer[]::new, Function.identity())).isNotNull().hasSameSizeAs(list)
                .containsExactlyElementsOf(list);
    }

    @Test
    public void testMapToArrayFromNullWithSameTypeMapper() throws Exception {
        assertThat(mapToArray(null, Integer[]::new, Function.identity())).isNull();
    }

    @Test
    public void testMapToArrayWithNullTypeMapper() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThatThrownBy(() -> mapToArray(list, Integer[]::new, null))
                .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testMapToListFromEmpty() throws Exception {
        Set<Integer> set = Sets.newHashSet(1);
        assertThat(mapToList(set, Function.identity())).isNotNull().containsExactlyElementsOf(set);
    }

    @Test
    public void testMapToListFromNull() throws Exception {
        assertThat(mapToList(null, Function.identity())).isNull();
    }

    @Test
    public void testMapToListWithNullMapper() throws Exception {
        Set<Integer> set = Sets.newHashSet(1);
        assertThatThrownBy(() -> mapToList(set, null)).isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    public void testMapToMapFromEmptyWithMappingFunction() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(mapToMap(list, Function.identity(), Function.identity())).isNotNull().isEmpty();
    }

    @Test
    public void testMapToMapWithMappingFunction() throws Exception {
        Date value = new Date(System.currentTimeMillis());
        List<Date> list = Collections.singletonList(value);

        assertThat(mapToMap(list, Date::getTime, Function.identity())).isNotNull().hasSameSizeAs(list)
                .containsKey(value.getTime())
                .containsValue(value);
    }

    @Test
    public void testMapToMapFromEmpty() throws Exception {
        List<Integer> list = Collections.emptyList();
        assertThat(mapToMap(list, Function.identity())).isNotNull().isEmpty();
    }

    @Test
    public void testMapToMap() throws Exception {
        Date value = new Date(System.currentTimeMillis());
        List<Date> list = Collections.singletonList(value);

        assertThat(mapToMap(list, Date::getTime)).isNotNull().hasSameSizeAs(list)
                .containsKey(value.getTime())
                .containsValue(value);
    }

    @Test
    public void testFilter() throws Exception {
        List<String> list = Collections.singletonList(null);
        Predicate<String> stringPredicate = s -> s != null;
        assertThat(filter(list, stringPredicate)).isNotNull().isEmpty();
    }

    @Test
    public void testForEach() throws Exception {
        List<String> list = Collections.singletonList("string");
        List<String> results = new ArrayList<>();
        forEach(list, results::add);

        assertThat(results).hasSameSizeAs(list).containsExactlyElementsOf(list);
    }

    @Test
    public void testForEachFromNull() throws Exception {
        List<String> list = null;
        List<String> results = new ArrayList<>();
        forEach(list, results::add);

        assertThat(results).isEmpty();
    }

    @Test
    public void testMapToListFromNullMap() throws Exception {
        Map<String, Long> map = null;
        BiFunction<String, Long, Long> stringLongLongBiFunction = (k, v) -> v;
        assertThat(mapToList(map, stringLongLongBiFunction)).isNull();
    }

    @Test
    public void testMapToListFromEmptyMap() throws Exception {
        Map<String, Long> map = Collections.emptyMap();
        BiFunction<String, Long, Long> stringLongLongBiFunction = (k, v) -> v;
        assertThat(mapToList(map, stringLongLongBiFunction)).isNotNull().isEmpty();
    }

    @Test
    public void testMapToListFromNonEmptyMap() throws Exception {
        long value = 1L;
        Map<String, Long> map = Maps.newHashMap("string", value);
        BiFunction<String, Long, Long> stringLongLongBiFunction = (k, v) -> v;
        assertThat(mapToList(map, stringLongLongBiFunction))
                .isNotNull()
                .hasSameSizeAs(map.entrySet())
                .containsExactly(value);
    }

    @Test
    public void testMapToArrayFromNullMap() throws Exception {
        Map<String, Long> map = null;
        BiFunction<String, Long, Long> stringLongLongBiFunction = (k, v) -> v;
        assertThat(mapToArray(map, Long[]::new, stringLongLongBiFunction)).isNull();
    }

    @Test
    public void testMapToArrayFromEmptyMap() throws Exception {
        Map<String, Long> map = Collections.emptyMap();
        BiFunction<String, Long, Long> stringLongLongBiFunction = (k, v) -> v;
        assertThat(mapToArray(map, Long[]::new, stringLongLongBiFunction)).isNotNull().isEmpty();
    }

    @Test
    public void testMapToArrayFromNonEmptyMap() throws Exception {
        long value = 1L;
        Map<String, Long> map = Maps.newHashMap("string", value);
        BiFunction<String, Long, Long> stringLongLongBiFunction = (k, v) -> v;
        assertThat(mapToArray(map, Long[]::new, stringLongLongBiFunction))
                .isNotNull()
                .hasSameSizeAs(map.entrySet())
                .containsExactly(value);
    }

    @Test
    public void testFindAny() throws Exception {
        Integer value = 5;
        List<Integer> list = Collections.singletonList(value);
        assertThat(findAny(list, integer -> integer > 0)).isNotEmpty().contains(value);
    }

    @Test
    public void testFindAnyEmpty() throws Exception {
        Integer value = 5;
        List<Integer> list = Collections.singletonList(value);
        assertThat(findAny(list, integer -> integer > 10)).isEmpty();
    }
}
