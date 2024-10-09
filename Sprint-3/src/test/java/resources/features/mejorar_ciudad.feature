Feature: Obtener recompensa
  Scenario: Como jugador quiero recibir recompensas por completar objetivos en el juego. para motivarme a seguir mejorando mi ciudad
    Given el jugador tiene una ciudad con un nivel actual de 1
    And el jugador ha construido 5 edificios
    When el sistema detecta que el jugador ha alcanzado el objetivo de 5 edificios
    Then la ciudad sube al nivel 2
    And el jugador recibe una recompensa de 10 unidades adicionales de cada recurso
