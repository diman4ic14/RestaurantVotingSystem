package ru.falin.RestaurantVotingSystem.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.falin.RestaurantVotingSystem.model.Vote;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class DataJpaVoteRepository implements VoteRepository {

    private static final Sort SORT_ID = Sort.by(Sort.Direction.DESC, "id");

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository,
                                 CrudUserRepository crudUserRepository,
                                 CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    @Transactional
    public Vote save(Vote vote, int restaurantId, int userId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id)
                .filter(v -> v.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.findAll(SORT_ID);
    }

    @Override
    public List<Vote> getFilteredByDay(LocalDateTime date) {
        return crudVoteRepository.getAllByDate(date);
    }
}
