using Microsoft.EntityFrameworkCore;
using Pomelo.EntityFrameworkCore.MySql;
using parking.Models;

namespace parking.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options)
        {
        }

        public DbSet<Place> Places { get; set; }
        public DbSet<Occupation> Occupations { get; set; }
        public DbSet<Reservation> Reservations { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseMySql("Server=localhost;Database=parking_db;User=root;Password=;", new MySqlServerVersion(new Version(8, 0, 0)));
            }
        }

    }
}
