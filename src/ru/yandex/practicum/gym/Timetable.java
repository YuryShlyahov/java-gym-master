package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        timetable.put(DayOfWeek.MONDAY, new TreeMap<>());
        timetable.put(DayOfWeek.TUESDAY, new TreeMap<>());
        timetable.put(DayOfWeek.WEDNESDAY, new TreeMap<>());
        timetable.put(DayOfWeek.THURSDAY, new TreeMap<>());
        timetable.put(DayOfWeek.FRIDAY, new TreeMap<>());
        timetable.put(DayOfWeek.SATURDAY, new TreeMap<>());
        timetable.put(DayOfWeek.SUNDAY, new TreeMap<>());
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek trainingDay = trainingSession.getDayOfWeek();
        Map<TimeOfDay, List<TrainingSession>> trainingSessionMap = new TreeMap<>();
        List<TrainingSession> trainingSessionList = new ArrayList<>();
        trainingSessionList.add(trainingSession);
        if (!timetable.containsKey(trainingDay)) {
            trainingSessionMap.put(trainingSession.getTimeOfDay(), trainingSessionList);
            timetable.put(trainingDay, trainingSessionMap);
        } else {
            TimeOfDay trainingTime = trainingSession.getTimeOfDay();
            trainingSessionMap = timetable.get(trainingDay);
            if (trainingSessionMap.containsKey(trainingTime)) {
                trainingSessionMap.get(trainingTime).add(trainingSession);
            } else {
                trainingSessionMap.put(trainingTime, trainingSessionList);
            }
        }
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if (!timetable.containsKey(dayOfWeek)) {
            return new TreeMap<>();
        }
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if (!timetable.containsKey(dayOfWeek)) {
            return new ArrayList<>();
        }
        Map<TimeOfDay, List<TrainingSession>> trainingSessionMap = timetable.get(dayOfWeek);
        if (!trainingSessionMap.containsKey(timeOfDay)) {
            return new ArrayList<>();
        }

        return trainingSessionMap.get(timeOfDay);
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> trainingsByCoaches = new HashMap<>();
        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();
        Coach coach;
        for (Map<TimeOfDay, List<TrainingSession>> dayOfWeekTrainings : timetable.values()) {
            for (List<TrainingSession> trainingSessionsOnTime : dayOfWeekTrainings.values()) {
                for (TrainingSession trainingSession : trainingSessionsOnTime) {
                    coach = trainingSession.getCoach();
                    if (trainingsByCoaches.containsKey(coach)) {
                        trainingsByCoaches.replace(coach, trainingsByCoaches.get(coach) + 1);
                    } else {
                        trainingsByCoaches.put(coach, 1);
                    }
                }
            }
        }
        for (Coach trainer : trainingsByCoaches.keySet()) {
            counterOfTrainings.add(new CounterOfTrainings(trainer, trainingsByCoaches.get(trainer)));
        }
        Collections.sort(counterOfTrainings);
        return counterOfTrainings;
    }
}
