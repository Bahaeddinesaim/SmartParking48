using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using parking.Data;
using parking.Models;

namespace parking.Controllers
{
    public class AccountController : Controller
    {
       private readonly ApplicationDbContext _contex ;
        public AccountController(ApplicationDbContext context)
        {
            _contex = context;
        }
        [HttpGet]
        public IActionResult Login()
        {
            return View();
        }
        [HttpPost]
        public IActionResult Login(LoginViewModel model)
        {
            if(ModelState.IsValid)
            {
                var user =_contex.Occupations.FirstOrDefault(u=>u.email==model.Email && u.randomPassword==model.Password);
                if(user == null)
                {
                    ViewBag.erreur = "Votre email ou password invaid !!";
                    return View(model);
                }
                else
                {
                    return RedirectToAction("ReservationDetails", new { proprio_id = user.proprio_id });

                }

            }
            
            return View();
        }

        public IActionResult Logout()
        {
            // Implémentez la logique de déconnexion ici
            return RedirectToAction("Login", "Account");
        }

        public IActionResult ReservationDetails(string proprio_id)
        {
            var reservation = _contex.Reservations
                .Include(r => r.Place)
                .Include(r => r.Occupation)
                .FirstOrDefault(r => r.idproprio == proprio_id);

            if (reservation == null)
            {
                return RedirectToAction("Index", "Home"); 
            }

            return View(reservation);
        }
    }
}
