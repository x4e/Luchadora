package cookiedragon.luchadora.event.client

/**
 * @author cookiedragon234 17/Feb/2020
 */
data class AllowedCharactersEvent(var state: State) {
	enum class State {
		ALLOW, ALLOW_SERVER, DISALLOW
	}
}
