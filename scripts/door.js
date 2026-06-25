function open(c) {
    var rot = c.subject.getStates().getNumber("rot") || 0
    c.subject.setRotations(0, 0, 90 * rot)
}

function close(c) {
    c.subject.setRotations(0, 0, 0)
}

function main(c) { // on tick update
    var rot = c.subject.getRotations()
    var st = c.subject.getStates().getNumber("state") || 0
    var currentRot = Math.floor(rot.z / 90) * 90
    c.subject.getStates().setNumber("rot", currentRot / 90)
    
    var p = c.subject.getPosition()
    var bp = Math.floor(p.y) - 1
    
    if (st == 1) {
        c.getWorld().setBlock(mappet.createBlockState("minecraft:air", 0), Math.floor(p.x), bp, Math.floor(p.z))
    } else {
        c.getWorld().setBlock(mappet.createBlockState("minecraft:barrier", 0), Math.floor(p.x), bp, Math.floor(p.z))
    }
}

function interact(c) {
    var st = c.subject.getStates()
    var r = st.getNumber("state") || 0
    
    if (r == 1) {
        close(c)
        st.setNumber("state", 0)
    } else {
        open(c)
        st.setNumber("state", 1)
    }
}
