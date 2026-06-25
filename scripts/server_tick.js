function save(c, data) {
  var st = JSON.parse(c.server.states.getString("game"))
  c.server.states.setString("game", st.push(data))
}

function get(c) {
  return JSON.parse(c.server.states.getString("game"))
}

function main(c) {
  var world = c.world
  var server = c.server

  if (get().state == true) {
    // nothing now
  }
}
