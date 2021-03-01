package com.mideros.diceGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mideros.diceGame.dto.Game;
import com.mideros.diceGame.dto.Player;

@Repository
@Transactional
public interface IGameRepository extends JpaRepository<Game, Integer> {

	public List<Game> getGameByPlayer(Player player);

	public void deleteGameByPlayer(Player player);
}
