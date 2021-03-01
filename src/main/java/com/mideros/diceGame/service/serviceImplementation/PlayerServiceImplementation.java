package com.mideros.diceGame.service.serviceImplementation;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mideros.diceGame.dto.Player;
import com.mideros.diceGame.exception.PlayerExistsException;
import com.mideros.diceGame.repository.IPlayerRepository;
import com.mideros.diceGame.service.IPlayerService;

@Service
public class PlayerServiceImplementation implements IPlayerService {

	@Autowired
	IPlayerRepository playerRepository;

	public PlayerServiceImplementation(IPlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public List<Player> listPlayers() {
		return playerRepository.findAll();
	}
	
	@Override
	public Player savePlayer(Player player) {

		List<Player> listP = playerRepository.findAll();
		boolean validPlayer = false;

		if (player.getName_player().isEmpty() || player.getName_player() == null) {
			player.setName_player("ANONYMOUS");
		}

		if (player.getName_player().length() >= 3) {

			if (listP.contains(player)) {
				validPlayer = false;

			} else {
				for (Player p : listP) {
					if (p.getName_player().equalsIgnoreCase(player.getName_player())) {
						validPlayer = false;
					} else {
						validPlayer = true;
					}
				}
			}
		} else {
			throw new PlayerExistsException("The name " + player.getName_player() + " is less than 3 characters!");
		}

		if (validPlayer) {
			return playerRepository.save(player);
		} else
			throw new PlayerExistsException("The player's name " + player.getName_player() + " is already registered.");
	}

	@Override
	public Player getPlayerById(int id_player) {
		return playerRepository.findById(id_player);
	}

	@Override
	public Player updatePlayer(int id_player, Player player) {

		Player oldDataPlayer = new Player();
		List<Player> listPUp = playerRepository.findAll();
		oldDataPlayer = playerRepository.findById(id_player);

		if (player.getName_player().isEmpty() || player.getName_player() == null) {

			player.setName_player("ANONYMOUS");
			oldDataPlayer.setName_player(player.getName_player());

			return playerRepository.save(oldDataPlayer);
		} else {
			if (player.getName_player().equalsIgnoreCase("ANONYMOUS")) {
				oldDataPlayer.setName_player(player.getName_player());

				return playerRepository.save(oldDataPlayer);
			} else {
				if (listPUp.contains(player)) {
					if (player.getId_player() == id_player) {
						return playerRepository.save(oldDataPlayer);
					} else {
						throw new PlayerExistsException("The Player: " + player.getName_player() + " already exists.");
					}
				} else {
					oldDataPlayer.setName_player(player.getName_player());
					return playerRepository.save(oldDataPlayer);
				}
			}
		}
	}

	@Override
	public void deletePlayer(int id_player) {
		playerRepository.deleteById(id_player);
	}

	@Override
	public Player rankingLoser() {
		List<Player> allPlayers = playerRepository.findAll();
		allPlayers.sort(Comparator.comparing(Player::getSuccess_rate_player));
		return allPlayers.get(0);
	}

	@Override
	public Player rankingWinner() {
		List<Player> allPlayersW = playerRepository.findAll();
		allPlayersW.sort(Comparator.comparing(Player::getSuccess_rate_player).reversed());
		return allPlayersW.get(0);
	}

	@Override
	public double averageRanking() {

		List<Player> allPlayersR = playerRepository.findAll();
		Double averageRanking = allPlayersR.stream().mapToDouble(Player::getSuccess_rate_player).average().orElse(0.0);
		return averageRanking;
	}
}
