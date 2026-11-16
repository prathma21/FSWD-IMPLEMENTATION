// Simple canvas animation: floating circles on the hero banner
const canvas = document.getElementById("heroCanvas");
const ctx = canvas.getContext("2d");
canvas.width = canvas.offsetWidth;
canvas.height = canvas.offsetHeight;

const dots = [];
for (let i = 0; i < 25; i++) {
  dots.push({
    x: Math.random() * canvas.width,
    y: Math.random() * canvas.height,
    r: Math.random() * 4 + 2,
    dy: Math.random() * 0.8 + 0.2
  });
}

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  ctx.fillStyle = "rgba(255,255,255,0.6)";
  dots.forEach(d => {
    ctx.beginPath();
    ctx.arc(d.x, d.y, d.r, 0, Math.PI * 2);
    ctx.fill();
    d.y -= d.dy;
    if (d.y < 0) d.y = canvas.height;
  });
  requestAnimationFrame(draw);
}
draw();
