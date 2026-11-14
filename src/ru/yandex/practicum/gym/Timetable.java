package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        timetable.get(trainingSession.getDayOfWeek())
                .computeIfAbsent(trainingSession.getTimeOfDay(), time -> new ArrayList<>())
                .add(trainingSession);
    }


    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> trainingSessionMap = timetable.get(dayOfWeek);
        if (!trainingSessionMap.containsKey(timeOfDay)) {
            return new ArrayList<>();
        }
        return trainingSessionMap.get(timeOfDay);
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
