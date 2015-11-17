class UrlMappings {

	static mappings = {
		"/clubs"(resources: 'club')
		"/leagues"(resources: 'league')
		"/leagues/addItalianLeague"(controller: "league", action: "addItalianLeague")
		"/leagues/deleteLeagues"(controller: "league", action: "deleteLeagues")
		"/leagues/players"(controller: "league", action: "players")
		"/leagues/cypherLeagues"(controller: "league", action: "cypherLeagues")
		"/clubs/$id/validate"(controller: "club", action: "validate", method: "PUT")

        "500"(view:'/error')
	}
}
