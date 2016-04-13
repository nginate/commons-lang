package com.github.commons.lang;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
@UtilityClass
public class NCollections {

    @Nullable
    public static <T> T getFirst(@NonNull Collection<T> collection) {
        return !collection.isEmpty() ? collection.iterator().next() : null;
    }

    @Nonnull
    public static <T> Optional<T> getFirstOptional(@NonNull Collection<T> collection) {
        return Optional.ofNullable(getFirst(collection));
    }

    @Nonnull
    public static <T> T requireFirst(@NonNull Collection<T> collection) {
        return getFirstOptional(collection).get();
    }

    @Nullable
    public static <T> T getSingle(@NonNull Collection<T> collection) {
        if (collection.size() > 1) {
            throw new IllegalArgumentException("Wrong collection size " + collection.size());
        }
        return getFirst(collection);
    }

    @Nonnull
    public static <T> Optional<T> getSingleOptional(@NonNull Collection<T> collection) {
        return Optional.ofNullable(getSingle(collection));
    }

    @Nonnull
    public static <T> T requireSingle(@NonNull Collection<T> collection) {
        return getSingleOptional(collection).get();
    }

    @Nullable
    public static <T, O> T[] mapToArray(@Nullable Collection<O> original,
            @NonNull IntFunction<T[]> generator, @NonNull Function<O, T> mapper) {
        return original != null ? original.stream().map(mapper).toArray(generator) : null;
    }

    @Nullable
    public static <T, O> List<T> mapToList(@Nullable Collection<O> original, @NonNull Function<O, T> mapper) {
        return original != null ? original.stream().map(mapper).collect(Collectors.toList()) : null;
    }

    @Nullable
    public static <K, V, O> Map<K, V> mapToMap(@Nullable Collection<O> original, @NonNull Function<O, K> keyMapper,
            @NonNull Function<O, V> valueMapper) {
        return original != null ? original.stream().collect(Collectors.toMap(keyMapper, valueMapper)) : null;
    }

    @Nullable
    public static <K, V> Map<K, V> mapToMap(@Nullable Collection<V> original, Function<V, K> keyMapper) {
        return mapToMap(original, keyMapper, e -> e);
    }

    @Nonnull
    public static <T> List<T> filter(@Nonnull Collection<T> original, @Nonnull Predicate<T> predicate) {
        return original.stream().filter(predicate).collect(Collectors.toList());
    }

    @Nonnull
    public static <T> Optional<T> findAny(@Nonnull Collection<T> original, @Nonnull Predicate<T> predicate) {
        return original.stream().filter(predicate).findAny();
    }

    public static <T> void forEach(@Nullable Collection<T> collection, @Nonnull Consumer<? super T> action) {
        if (collection != null) {
            collection.forEach(action);
        }
    }

    @Nullable
    public static <K, V, T> List<T> mapToList(@Nullable Map<K, V> original, @NonNull Function<Map.Entry<K, V>, T>
            mapper) {
        return original != null ? original.entrySet().stream().map(mapper).collect(Collectors.toList()) : null;
    }
}
