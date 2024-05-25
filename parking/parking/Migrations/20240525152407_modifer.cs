using Microsoft.EntityFrameworkCore.Migrations;
using MySql.EntityFrameworkCore.Metadata;

#nullable disable

namespace parking.Migrations
{
    public partial class modifer : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterDatabase()
                .Annotation("MySQL:Charset", "utf8mb4");

            migrationBuilder.CreateTable(
                name: "tb_place",
                columns: table => new
                {
                    num_place = table.Column<int>(type: "int", nullable: false)
                        .Annotation("MySQL:ValueGenerationStrategy", MySQLValueGenerationStrategy.IdentityColumn),
                    type_place = table.Column<string>(type: "longtext", nullable: false),
                    disponible = table.Column<string>(type: "longtext", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tb_place", x => x.num_place);
                })
                .Annotation("MySQL:Charset", "utf8mb4");

            migrationBuilder.CreateTable(
                name: "tb_occupation",
                columns: table => new
                {
                    proprio_id = table.Column<string>(type: "varchar(255)", nullable: false),
                    proprio_nom = table.Column<string>(type: "longtext", nullable: false),
                    mt_type = table.Column<string>(type: "longtext", nullable: false),
                    mt_matricule = table.Column<string>(type: "longtext", nullable: false),
                    id_place = table.Column<int>(type: "int", nullable: false),
                    email = table.Column<string>(type: "longtext", nullable: false),
                    randomPassword = table.Column<string>(type: "longtext", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tb_occupation", x => x.proprio_id);
                    table.ForeignKey(
                        name: "FK_tb_occupation_tb_place_id_place",
                        column: x => x.id_place,
                        principalTable: "tb_place",
                        principalColumn: "num_place",
                        onDelete: ReferentialAction.Cascade);
                })
                .Annotation("MySQL:Charset", "utf8mb4");

            migrationBuilder.CreateTable(
                name: "tb_reservation",
                columns: table => new
                {
                    id_reservation = table.Column<int>(type: "int", nullable: false)
                        .Annotation("MySQL:ValueGenerationStrategy", MySQLValueGenerationStrategy.IdentityColumn),
                    date_debut = table.Column<string>(type: "longtext", nullable: false),
                    date_expiration = table.Column<string>(type: "longtext", nullable: false),
                    id_place = table.Column<int>(type: "int", nullable: false),
                    idproprio = table.Column<string>(type: "varchar(255)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_tb_reservation", x => x.id_reservation);
                    table.ForeignKey(
                        name: "FK_tb_reservation_tb_occupation_idproprio",
                        column: x => x.idproprio,
                        principalTable: "tb_occupation",
                        principalColumn: "proprio_id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_tb_reservation_tb_place_id_place",
                        column: x => x.id_place,
                        principalTable: "tb_place",
                        principalColumn: "num_place",
                        onDelete: ReferentialAction.Cascade);
                })
                .Annotation("MySQL:Charset", "utf8mb4");

            migrationBuilder.CreateIndex(
                name: "IX_tb_occupation_id_place",
                table: "tb_occupation",
                column: "id_place");

            migrationBuilder.CreateIndex(
                name: "IX_tb_reservation_id_place",
                table: "tb_reservation",
                column: "id_place");

            migrationBuilder.CreateIndex(
                name: "IX_tb_reservation_idproprio",
                table: "tb_reservation",
                column: "idproprio");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "tb_reservation");

            migrationBuilder.DropTable(
                name: "tb_occupation");

            migrationBuilder.DropTable(
                name: "tb_place");
        }
    }
}
