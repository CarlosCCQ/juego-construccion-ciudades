Feature: Creacion y gestion de recursos

  Scenario: El jugador puede generar recursos manualmente para su ciudad
    Given el jugador tiene generadores de recursos asignados a su ciudad
    When el jugador decide generar recursos manualmente
    Then los recursos se añaden a los recursos actuales de la ciudad

  Scenario: Los generadores de recursos crean recursos automáticamente
    Given una ciudad tiene generadores de recursos activos
    When el tiempo programado de generación de recursos se cumple
    Then los generadores de recursos añaden más recursos a la ciudad