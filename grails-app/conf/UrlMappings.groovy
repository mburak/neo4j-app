class UrlMappings {

	static mappings = {
		"/clubs"(resources: 'club')
		"/leagues"(resources: 'league')
		"/tags"(resources: 'tag')
		"/leagues/addItalianLeague"(controller: "league", action: "addItalianLeague")
		"/leagues/deleteLeagues"(controller: "league", action: "deleteLeagues")
		"/leagues/players"(controller: "league", action: "players")
		"/leagues/cypherLeagues"(controller: "league", action: "cypherLeagues")
		"/clubs/$id/validate"(controller: "club", action: "validate", method: "PUT")
		"/clubs/$id/validateManualBinding"(controller: "club", action: "validateManualBinding", method: "PUT")
		"/clubs/addPlayer"(controller: "club", action: "addPlayer")
		"/clubs/updatePlayers"(controller: "club", action: "updatePlayers")
		"/leagues/addClubs"(controller: "league", action: "addClubs")
		"/clubs/$id/removeCountry"(controller: "club", action: "removeCountry", method: "GET")
		"/clubs/$id/removeCaptain"(controller: "club", action: "removeCaptain", method: "GET")

        "500"(view:'/error')
	}
}
