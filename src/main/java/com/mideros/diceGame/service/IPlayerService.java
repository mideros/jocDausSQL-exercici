package com.mideros.diceGame.service;

import java.util.List;

import com.mideros.diceGame.dto.Player;

public interface IPlayerService {

	public List<Player> listPlayers();

	public Player savePlayer(Player player);

	public Player getPlayerById(int id_player);

	public Player updatePlayer(int id_player,Player player);

	public void deletePlayer(int id_player);

	public double averageRanking();

	public Player rankingLoser();

	public Player rankingWinner();

}