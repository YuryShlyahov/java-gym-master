package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private final Coach coach;
    private final int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        int countComparison = Integer.compare(o.count, this.count);
        if (countComparison != 0) {
            return countComparison;
        } else {
            return this.coach.getSurname().compareTo(o.coach.getSurname());
        }
    }

    public Coach getCoach() {
        return coach;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return count == that.count && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }
}
