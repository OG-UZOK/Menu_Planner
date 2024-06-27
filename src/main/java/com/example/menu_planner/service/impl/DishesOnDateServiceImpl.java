package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.UnauthorizedException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.DishesOnDateRequest;
import com.example.menu_planner.model.dtoInput.DishesOnWeekRequest;
import com.example.menu_planner.model.dtoOutput.CaloriesOnWeekResponse;
import com.example.menu_planner.model.dtoOutput.DishesOnWeekResponse;
import com.example.menu_planner.model.entity.Dish;
import com.example.menu_planner.model.entity.DishesOnDate;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.DeletedTokensRepository;
import com.example.menu_planner.repository.DishRepository;
import com.example.menu_planner.repository.DishesOnDateRepository;
import com.example.menu_planner.service.DishesOnDateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class DishesOnDateServiceImpl implements DishesOnDateService {
    private final DishesOnDateRepository dishesOnDateRepository;
    private final DishRepository dishRepository;
    private final JwtTokenUtils tokenUtils;
    private final DeletedTokensRepository deletedTokensRepository;


    @SneakyThrows
    public CaloriesOnWeekResponse createPlanOnWeek(@Valid DishesOnWeekRequest request, Authentication authentication, String token){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }

        Integer totalCalories = 0;
        Integer totalProteins = 0;
        Integer totalFats = 0;
        Integer totalCarbohydrates = 0;

        dishesOnDateRepository.deleteAllByDateAndUser_id(request.monday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.tuesday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.wednesday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.thursday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.friday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.saturday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.sunday().date(), userId);


        if (request.monday().dishes() != null) {
            for (DishesOnDateRequest day : request.monday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.monday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.tuesday().dishes() != null) {
            for (DishesOnDateRequest day : request.tuesday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.tuesday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.wednesday().dishes() != null) {
            for (DishesOnDateRequest day : request.wednesday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.wednesday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.thursday().dishes() != null) {
            for (DishesOnDateRequest day : request.thursday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.thursday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.friday().dishes() != null) {
            for (DishesOnDateRequest day : request.friday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.friday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.saturday().dishes() != null) {
            for (DishesOnDateRequest day : request.saturday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.saturday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.sunday().dishes() != null) {
            for (DishesOnDateRequest day : request.sunday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.sunday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }


        CaloriesOnWeekResponse caloriesOnWeekResponse = new CaloriesOnWeekResponse(totalCalories, totalProteins, totalFats, totalCarbohydrates);
        return caloriesOnWeekResponse;
    }

    @SneakyThrows
    public CaloriesOnWeekResponse redactPlanOnWeek(@Valid DishesOnWeekRequest request, Authentication authentication, String token){
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }

        Integer totalCalories = 0;
        Integer totalProteins = 0;
        Integer totalFats = 0;
        Integer totalCarbohydrates = 0;

        System.out.println(request.monday() != null);

        dishesOnDateRepository.deleteAllByDateAndUser_id(request.monday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.tuesday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.wednesday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.thursday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.friday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.saturday().date(), userId);
        dishesOnDateRepository.deleteAllByDateAndUser_id(request.sunday().date(), userId);

        if (request.monday().dishes() != null) {
            for (DishesOnDateRequest day : request.monday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.monday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.tuesday().dishes() != null) {
            for (DishesOnDateRequest day : request.tuesday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.tuesday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.wednesday().dishes() != null) {
            for (DishesOnDateRequest day : request.wednesday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.wednesday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.thursday().dishes() != null) {
            for (DishesOnDateRequest day : request.thursday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.thursday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.friday().dishes() != null) {
            for (DishesOnDateRequest day : request.friday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.friday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.saturday().dishes() != null) {
            for (DishesOnDateRequest day : request.saturday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.saturday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }

        if (request.sunday().dishes() != null) {
            for (DishesOnDateRequest day : request.sunday().dishes()) {
                Dish currentDish = dishRepository.findById(day.dish_id()).orElseThrow(() ->
                        new NotFoundException("Dish with id:" + day.dish_id() + "does not exist"));
                DishesOnDate dishesOnDate = DishesOnDate.of(null, userId, currentDish.getId(), request.sunday().date(), day.number());
                dishesOnDateRepository.save(dishesOnDate);
                totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
            }
        }


        CaloriesOnWeekResponse caloriesOnWeekResponse = new CaloriesOnWeekResponse(totalCalories, totalProteins, totalFats, totalCarbohydrates);
        return caloriesOnWeekResponse;
    }

    @SneakyThrows
    public DishesOnWeekResponse getDishesOnWeek(@Valid LocalDate startDate, @Valid LocalDate endDate, Authentication authentication, String token) {
        UUID userId = tokenUtils.getUserIdFromAuthentication(authentication);
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }

        System.out.println(startDate.isAfter(endDate));
        System.out.println(ChronoUnit.DAYS.between(startDate, endDate));
        if (startDate.isAfter(endDate) || ChronoUnit.DAYS.between(startDate, endDate) != 6) {
            throw new WrongData("startDate and endDate are not correct");
        }

        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        List<Dish> monday = new ArrayList<>();
        List<Dish> tuesday = new ArrayList<>();
        List<Dish> wednesday = new ArrayList<>();
        List<Dish> thursday = new ArrayList<>();
        List<Dish> friday = new ArrayList<>();
        List<Dish> saturday = new ArrayList<>();
        List<Dish> sunday = new ArrayList<>();

        Integer totalCalories = 0;
        Integer totalProteins = 0;
        Integer totalFats = 0;
        Integer totalCarbohydrates = 0;

        int i = 0;
        for (LocalDate date : dates) {
            List<DishesOnDate> dishesOnDateList = dishesOnDateRepository.findAllByDateAndUser_id(date, userId);
            List<Dish> dishList = new ArrayList<>();

            if (dishesOnDateList != null) {
                for (DishesOnDate dishesOnDate : dishesOnDateList) {
                    Dish currentDish = dishRepository.findById(dishesOnDate.getDish_id()).orElseThrow(() ->
                            new NotFoundException("Dish with id:" + dishesOnDate.getDish_id() + " does not exist"));
                    dishList.add(currentDish);

                    totalCalories += currentDish.getCalories() / currentDish.getAmountPortion();
                    totalProteins += currentDish.getProteins() / currentDish.getAmountPortion();
                    totalFats += currentDish.getFats() / currentDish.getAmountPortion();
                    totalCarbohydrates += currentDish.getCarbohydrates() / currentDish.getAmountPortion();
                }
            }

            switch (i) {
                case 0 -> monday.addAll(dishList);
                case 1 -> tuesday.addAll(dishList);
                case 2 -> wednesday.addAll(dishList);
                case 3 -> thursday.addAll(dishList);
                case 4 -> friday.addAll(dishList);
                case 5 -> saturday.addAll(dishList);
                case 6 -> sunday.addAll(dishList);
            }
            i++;
        }

        CaloriesOnWeekResponse caloriesOnWeekResponse = new CaloriesOnWeekResponse(totalCalories, totalProteins, totalFats, totalCarbohydrates);


        return new DishesOnWeekResponse(monday, tuesday, wednesday, thursday, friday, saturday, sunday, caloriesOnWeekResponse);
    }

}
