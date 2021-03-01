package com.mideros.diceGame.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mideros.diceGame.dto.Game;
import com.mideros.diceGame.dto.Player;
import com.mideros.diceGame.exception.PlayerExistsException;
import com.mideros.diceGame.service.IGameService;
import com.mideros.diceGame.service.IPlayerService;

@RestController
@RequestMapping("/v1")
public class GameController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IPlayerService playerService;

	// roll the dice
	@PostMapping(value = { "/players/{id}/games" }, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> rollDice(@PathVariable(value = "id") int idPlayer,
			@Valid @RequestBody Player player) {

		try {
			if (playerService.getPlayerById(idPlayer) != null) {
				return ResponseEntity.ok().body(gameService.rollDice(idPlayer, player));

			} else {
				throw new PlayerExistsException("Player " + idPlayer + " does not exist!!!");
			}
		} catch (PlayerExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	// Get games by player
	@GetMapping(value = { "/players/{id}/games" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Game> getGameByPlayerById(@PathVariable(value = "id") int idPlayer) {

		if (playerService.getPlayerById(idPlayer) != null) {
			Player pById = playerService.getPlayerById(idPlayer);
			return gameService.getGameByPlayer(pById);
		} else {
			throw new PlayerExistsException("Player " + idPlayer + " does not exist!!!");
		}
	}

	/*
	 * // Delete games by player
	 * 
	 * @DeleteMapping("/players/{id}/games") public ResponseEntity<String>
	 * deleteGames(@PathVariable(value = "id") int idPlayer) {
	 * 
	 * List<Game> listGameByPlayer = new ArrayList<Game>(); try { if
	 * (playerService.getPlayerById(idPlayer) != null) { Player pById =
	 * playerService.getPlayerById(idPlayer); listGameByPlayer =
	 * gameService.getGameByPlayer(pById); gameService.deleteGameByPlayer(pById);
	 * pById.setSuccess_rate_player(0.0); playerService.updatePlayer(idPlayer,
	 * pById); return ResponseEntity.ok().body(listGameByPlayer.size() +
	 * " records have been deleted"); } else { throw new
	 * PlayerExistsException("Player " + idPlayer + " does not exist!!!"); } } catch
	 * (Exception e) { System.out.println(e.getMessage()); return
	 * ResponseEntity.ok().body("" + e); } }
	 */

	// Delete games by player
	@DeleteMapping("/players/{id}/games")
	public ResponseEntity<Object> deleteGames(@PathVariable(value = "id") int idPlayer) {

		if (playerService.getPlayerById(idPlayer) != null) {
			Player pById = playerService.getPlayerById(idPlayer);
			return ResponseEntity.ok().body(gameService.deleteGameByPlayer(pById));
		} else {
			throw new PlayerExistsException("Player " + idPlayer + " does not exist!!!");
		}
	}
}
