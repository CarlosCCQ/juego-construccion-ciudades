Feature: Creacion y gestion de recursos

  Scenario: El jugador puede generar recursos manualmente para su ciudad
    Given el jugador tiene generadores de recursos asignados a su ciudad
    When el jugador decide generar recursos manualmente
    Then los recursos se añaden a los recursos actuales de la ciudad

  Scenario: Los generadores de recursos crean recursos automáticamente
    Given una ciudad tiene generadores de recursos activos
    When el tiempo programado de generación de recursos se cumple
    Then los generadores de recursos añaden más recursos a la ciudad

  Scenario: Crear una nueva ciudad con recursos iniciales
    Given que no tengo ninguna ciudad creada
    When creo una ciudad llamada "CiudadDelSol"
    Then la ciudad "CiudadDelSol" debe tener recursos iniciales asignados
    And debe tener 100 unidades de piedra
    And debe tener 100 unidades de oro
    And debe tener 100 unidades de agua

  Scenario: Crear una nueva ciudad con generadores de recursos
    Given que no tengo ninguna ciudad creada
    When creo una ciudad llamada "Ciudad del Agua"
    Then la ciudad "Ciudad del Agua" debe tener 3 generadores de recursos asignados
    And debe tener un generador de tipo "CANTERA" que genera "PIEDRA"
    And debe tener un generador de tipo "MINA" que genera "ORO"
    And debe tener un generador de tipo "RIO" que genera "AGUA"