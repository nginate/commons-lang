package com.github.nginate.commons.lang;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

/**
 * Functional utilities to operate over java collections
 *
 * @since 1.0
 */
@SuppressWarnings({"WeakerAccess", "OptionalGetWithoutIsPresent"})
@UtilityClass
public class NCollections {

    /**
     * Return first element of collection or null if collection is null or is empty
     *
     * @param collection generic collection
     * @param <T>        type of collection
     * @return first element ot null
     */
    @Nullable
    public static <T> T getFirst(@NonNull Collection<T> collection) {
        return !collection.isEmpty() ? collection.iterator().next() : null;
    }

    /**
     * Return first element of collection, wrapping it in {@code Optional}. If collection is null or is empty,
     * the result of operation will be empty optional.
     *
     * @param collection generic collection
     * @param <T>        type of collection
     * @return optional of first element if present ot empty
     */
    @Nonnull
    public static <T> Optional<T> getFirstOptional(@NonNull Collection<T> collection) {
        return Optional.ofNullable(getFirst(collection));
    }

    /**
     * Return first element of collection. Will throw NoSuchElementException if collection is null or empty
     *
     * @param collection generic collection
     * @param <T>        type of collection
     * @return the non-null value of first element
     * @throws NoSuchElementException if there is no value present
     * @see Optional#isPresent()
     */
    @Nonnull
    public static <T> T requireFirst(@NonNull Collection<T> collection) {
        return getFirstOptional(collection).get();
    }

    /**
     * Transform collection of one element into this one element (retrieve it). Will return null if collection is null
     * or empty
     *
     * @param collection generic collection
     * @param <T>        type of collection
     * @return first and single existing element of collection or null
     * @throws IllegalArgumentException if collection has more than one elements
     * @see NCollections#getFirst(Collection)
     */
    @Nullable
    public static <T> T getSingle(@NonNull Collection<T> collection) {
        if (collection.size() > 1) {
            throw new IllegalArgumentException("Wrong collection size " + collection.size());
        }
        return getFirst(collection);
    }

    /**
     * Transform collection of one element into {@code Optional} of this element. Will return {@code Optional.empty()}
     * if collection is null or empty
     *
     * @param collection generic collection
     * @param <T>        type of collection
     * @return optional of first and single existing element of collection or {@code Optional.empty()}
     * @throws IllegalArgumentException if collection has more than one elements
     * @see NCollections#getSingle(Collection)
     */
    @Nonnull
    public static <T> Optional<T> getSingleOptional(@NonNull Collection<T> collection) {
        return Optional.ofNullable(getSingle(collection));
    }

    /**
     * Return non-null element from a collection, containing only one element.
     *
     * @param collection generic collection
     * @param <T>        type of collection
     * @return single non-null element from a collection
     * @throws IllegalArgumentException if collection has more than one elements
     * @throws NoSuchElementException   if there is no value present
     */
    @Nonnull
    public static <T> T requireSingle(@NonNull Collection<T> collection) {
        return getSingleOptional(collection).get();
    }

    /**
     * Convert collection to an array of same type. Will return null if original collection is null
     *
     * @param original  generic collection
     * @param generator array provider
     * @param <T>       type of elements
     * @return array, containing all elements of collection, or null if source is null
     * @see NCollections#mapToArray(Collection, IntFunction, Function)
     */
    @Nullable
    public static <T> T[] mapToArray(@Nullable Collection<T> original, @NonNull IntFunction<T[]> generator) {
        return mapToArray(original, generator, identity());
    }

    /**
     * Convert collection of one type to an array of other type using provided mapping function. Will return null if
     * original collection is null
     *
     * @param original  source collection
     * @param generator array provider
     * @param mapper    function to convert source element type to dest element type
     * @param <T>       destination type
     * @param <O>       source type
     * @return array, containing all elements of collection, or null if source is null
     */
    @Nullable
    public static <T, O> T[] mapToArray(@Nullable Collection<O> original,
                                        @NonNull IntFunction<T[]> generator, @NonNull Function<O, T> mapper) {
        return original != null ? original.stream().map(mapper).toArray(generator) : null;
    }

    /**
     * Convert source collection of type O to a list of type T using provided mapping function. Will return null if
     * source collection is null
     *
     * @param original source collection
     * @param mapper   function to convert source element type to dest element type
     * @param <T>      destination type
     * @param <O>      source type
     * @return list, containing all elements of collection, or null if source is null
     */
    @Nullable
    public static <T, O> List<T> mapToList(@Nullable Collection<O> original, @NonNull Function<O, T> mapper) {
        return original != null ? original.stream().map(mapper).collect(Collectors.toList()) : null;
    }

    /**
     * Convert source collection to a map, using two provided functions to extract keys and values from source elements.
     * Will return null if source is null
     *
     * @param original    source collection
     * @param keyMapper   function to map map key from source element
     * @param valueMapper function to map map value from source element
     * @param <K>         key type
     * @param <V>         value type
     * @param <O>         source type
     * @return map, containing mapped source elements, or null if source is null
     */
    @Nullable
    public static <K, V, O> Map<K, V> mapToMap(@Nullable Collection<O> original, @NonNull Function<O, K> keyMapper,
                                               @NonNull Function<O, V> valueMapper) {
        return original != null ? original.stream().collect(Collectors.toMap(keyMapper, valueMapper)) : null;
    }

    /**
     * Convert source collection to a map, using provided function to extract keys from source elements.
     * Will return null if source is null
     *
     * @param original  source collection
     * @param keyMapper function to map map key from source element
     * @param <K>       key type
     * @param <V>       value and source type
     * @return map, containing all source elements as values, or null if source is null
     */
    @Nullable
    public static <K, V> Map<K, V> mapToMap(@Nullable Collection<V> original, Function<V, K> keyMapper) {
        return mapToMap(original, keyMapper, identity());
    }

    /**
     * Get a copy of source collection without elements that do not fit to a predicate conditions
     *
     * @param original  source collection
     * @param predicate source element predicate
     * @param <T>       source type
     * @return collection copy with matched elements
     */
    @Nonnull
    public static <T> List<T> filter(@Nonnull Collection<T> original, @Nonnull Predicate<T> predicate) {
        return original.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Get any element from provided collection that matches predicate conditions
     *
     * @param original  source collection
     * @param predicate element predicate
     * @param <T>       source type
     * @return any matched element
     * @see Stream#findAny()
     */
    @Nonnull
    public static <T> Optional<T> findAny(@Nonnull Collection<T> original, @Nonnull Predicate<T> predicate) {
        return original.stream().filter(predicate).findAny();
    }

    /**
     * Execute provided action over each element of source collection if it is not null, skip otherwise
     *
     * @param collection source collection
     * @param action     element consumer
     * @param <T>        source type
     */
    public static <T> void forEach(@Nullable Collection<T> collection, @Nonnull Consumer<? super T> action) {
        if (collection != null) {
            collection.forEach(action);
        }
    }

    /**
     * Convert map to a list, using provided function to combine map entry to a destination list type
     *
     * @param original source map
     * @param mapper   function to combine amd convert map entry to a required type
     * @param <K>      key type
     * @param <V>      value type
     * @param <T>      destination type
     * @return list, containing mapped entries, or null if source is null
     */
    @Nullable
    public static <K, V, T> List<T> mapToList(@Nullable Map<K, V> original,
                                              @NonNull BiFunction<K, V, T> mapper) {
        if (original == null) {
            return null;
        }
        return mapToList(original.entrySet(), entry -> mapper.apply(entry.getKey(), entry.getValue()));
    }

    /**
     * Convert map to an array, using provided function to combine map entry to a destination array type. Will return
     * null if source is null
     *
     * @param original  source map
     * @param generator array provider
     * @param mapper    function to combine amd convert map entry to a required type
     * @param <K>       map key type
     * @param <V>       map value type
     * @param <T>       array type
     * @return array, containing mapped entries, or null if source is null
     */
    @Nullable
    public static <K, V, T> T[] mapToArray(@Nullable Map<K, V> original,
                                           @NonNull IntFunction<T[]> generator,
                                           @NonNull BiFunction<K, V, T> mapper) {
        if (original == null) {
            return null;
        }
        return mapToArray(original.entrySet(), generator, entry -> mapper.apply(entry.getKey(), entry.getValue()));
    }
}
